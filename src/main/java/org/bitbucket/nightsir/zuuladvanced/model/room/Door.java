package org.bitbucket.nightsir.zuuladvanced.model.room;

/**
 * Representation for a door that can be locked.
 * 
 * @author Christian Sami
 */
public class Door {
	private String roomBehind;
	private boolean locked;

	/**
	 * Constructs a new lockable/unlockable door.
	 * 
	 * @param roomBehind id of the room behind
	 * @param locked if the door is locked
	 */
	public Door(String roomBehind, boolean locked) {
		this.roomBehind = roomBehind;
		this.locked = locked;
	}

	/**
	 * Checks if the door is locked
	 * 
	 * @return if the door is locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Unlocks the door.
	 */
	public void unlock() {
		locked = false;
	}

	/**
	 * Gets the room behind the door.
	 * 
	 * @return id of the room behind
	 */
	public String getRoomBehind() {
		return roomBehind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + ((roomBehind == null) ? 0 : roomBehind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Door other = (Door) obj;
		if (locked != other.locked)
			return false;
		if (roomBehind == null) {
			if (other.roomBehind != null)
				return false;
		} else if (!roomBehind.equals(other.roomBehind))
			return false;
		return true;
	}
}
