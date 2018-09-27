package apps.wazzzainvst.montyhall;

public class Door {

    int doorNumber;
    int doorContents;
    public Door (int aDoorNumber){

        doorNumber = aDoorNumber;

    }
    public void setDoorContents(int doorContents) {

        //if 1, then we have a zonk
        //if 2, then we have a car
        this.doorContents = doorContents;
    }

    public int getDoorContents() {
        return doorContents;
    }
}
