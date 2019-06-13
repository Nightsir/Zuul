package org.bitbucket.nightsir.zuuladvanced.ui;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.model.item.Item;
import org.bitbucket.nightsir.zuuladvanced.model.item.Sellable;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.NonAllowedActionTypeException;
import org.bitbucket.nightsir.zuuladvanced.model.npc.proxytyping.AbstractTrader;
import org.bitbucket.nightsir.zuuladvanced.model.npc.types.returntypes.TradeItem;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.datawrapper.TradeItemWrapper;
import org.bitbucket.nightsir.zuuladvanced.ui.layoutfix.CloseableController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.WindowEvent;

/**
 * Controller for the trade-window.
 * 
 * @author Christian Sami
 */
public class TradeUIController implements CloseableController {
	@FXML
	private Label traderName;
	@FXML
	private TableView<TradeItemWrapper> traderInv;
	@FXML
	private TableColumn<TradeItemWrapper, String> traderItemColumn;
	@FXML
	private TableColumn<TradeItemWrapper, Integer> traderValueColumn;
	
	@FXML
	private Label wallet;
	@FXML
	private TableView<TradeItemWrapper> playerInv;
	@FXML
	private TableColumn<TradeItemWrapper, String> playerItemColumn;
	@FXML
	private TableColumn<TradeItemWrapper, Integer> playerValueColumn;
	
	private AbstractTrader trader;
	
	@FXML
    private void initialize() {
		traderInv.setPlaceholder(new Label(Configuration.get("txt_trade_item")));
		traderItemColumn.setCellValueFactory((d) -> d.getValue().itemNameProperty());
		traderValueColumn.setCellValueFactory((d) -> d.getValue().valueProperty());

		playerInv.setPlaceholder(new Label(Configuration.get("txt_trade_item")));
		playerItemColumn.setCellValueFactory((d) -> d.getValue().itemNameProperty());
		playerValueColumn.setCellValueFactory((d) -> d.getValue().valueProperty());
    }
	
	@Override
	public void onExit(WindowEvent event) {
		try {
			if (trader.isReseller()) {
				trader.invokeAction(
						NPCActionType.TRADE_BUY_UPDATE,
						traderInv.getItems().stream()
								.map(TradeItemWrapper::getTradeItem)
								.toArray());
			}
		} catch (NonAllowedActionTypeException e) {}
	}
	
	private void openNoSelectionDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(Configuration.get("tit_tradesel"));
		alert.setHeaderText(null);
		alert.setContentText(Configuration.get("txt_trade_select"));
		alert.show();
	}
	
	@FXML
	private void buyItem() {
		TradeItemWrapper tradeItem = traderInv.getSelectionModel().getSelectedItem();
		
		if (tradeItem != null) {
			if (InventoryState.get().pay(tradeItem.getValue())) {
				InventoryState.get().addAmountOfItem(tradeItem.getItemName(), 1);
			}
		} else {
			openNoSelectionDialog();
		}
	}
	
	@FXML
	private void sellItem() {
		TradeItemWrapper tradeItem = playerInv.getSelectionModel().getSelectedItem();
		
		if (tradeItem != null) {
			InventoryState.get().removeAmountOfItem(tradeItem.getItemName(), 1);
			InventoryState.get().income(tradeItem.getValue());
			
			if (trader.isReseller()) {
				if (!traderInv.getItems().contains(tradeItem)) {
					Item item = ItemLoader.get().getItem(tradeItem.getItemName());
					traderInv.getItems().add(convertToWrapperTrader(item));
				}
			}
			
			updatePlayerInventory();
		} else {
			openNoSelectionDialog();
		}
	}
	
	private TradeItemWrapper convertToTradeItemWrapper(Item item, NPCActionType tradeType) {
		String itemId = ItemLoader.get().getItemIdForName(item.getName());
		Integer value = ((Sellable)item).getValue();
		Double valueModifier = 1.0;
		try {
			valueModifier = (Double) trader.invokeAction(tradeType, item.getClass().getSimpleName() + ";" + itemId);
		} catch (NonAllowedActionTypeException e) {}
		return new TradeItemWrapper(itemId, item.getName(), (int) (value * valueModifier));
	}
	
	private TradeItemWrapper convertToWrapperTrader(Item item) {
		return convertToTradeItemWrapper(item, NPCActionType.TRADE_BUY_VALUE_MOD);
	}
	
	private TradeItemWrapper convertToWrapperPlayer(Item item) {
		return convertToTradeItemWrapper(item, NPCActionType.TRADE_SELL_VALUE_MOD);
	}
	
	private void updatePlayerInventory() {
		wallet.setText(Configuration.get("txt_wallet") + InventoryState.get().getWallet());
		
		ObservableList<TradeItemWrapper> playerItems = FXCollections.observableArrayList(
				InventoryState.get().getDropableItemNames()
					.map((n) -> ItemLoader.get().getItem(n))
					.filter((i) -> Sellable.class.isInstance(i))
					.map(this::convertToWrapperPlayer)
					.filter((i) -> i.getValue() != 0)
					.collect(Collectors.toList()));
		playerInv.setItems(playerItems);
	}
	
	private void updateTraderInventory() {
		try {
			ObservableList<TradeItemWrapper> tradeItems = FXCollections.observableArrayList(
					Arrays.stream(
							(TradeItem[]) trader.invokeAction(NPCActionType.TRADE_BUY_GET, Void.TYPE))
						.map((i) -> new TradeItemWrapper(i))
						.collect(Collectors.toList()));
			traderInv.setItems(tradeItems);
		} catch (NonAllowedActionTypeException e) {}
	}
	
	/**
	 * Setter for the trader to trade with.
	 * 
	 * @param trader to trade with
	 */
	public void setTrader(AbstractTrader trader) {
		this.trader = trader;
		traderName.setText(trader.getName() + ":");
		updateTraderInventory();
		updatePlayerInventory();
	}
}
