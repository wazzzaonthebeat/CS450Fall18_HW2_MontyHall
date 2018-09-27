package apps.wazzzainvst.montyhall;

import java.util.Random;

public class Game {

    private Door door1 ;
    private Door door2 ;
    private Door door3 ;
    int carDoor;
    int goatDoor1;
    int goatDoor2;
    int switchDoor;


    public Game(){

        //create door objects and set the contents of the door
        door1 = new Door(1);
        door2 = new Door(2);
        door3 = new Door(3);


        Random rand = new Random();

        int  n = rand.nextInt(3) + 1;
        System.out.println("GENERATED: "+n);
        //p
        switch (n){

            case 1:
                //car is in door 1
                door1.setDoorContents(2);
                carDoor = 1;
                // door1Button.setImageDrawable(getResources().getDrawable(R.drawable.car));
                //door 2 is a goat
                door2.setDoorContents(1);
                goatDoor1 = 2;
                // door2Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));

                //door 3 is a goat
                door3.setDoorContents(1);
                goatDoor2 = 3;
                //door3Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));

                break;
            case 2:

                //car is in door 2
                door2.setDoorContents(2);
                carDoor = 2;

                //  door2Button.setImageDrawable(getResources().getDrawable(R.drawable.car));

                //door 1 is a goat
                door1.setDoorContents(1);
                goatDoor1 = 1;
                // door1Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));

                //door 3 is a goat
                door3.setDoorContents(1);
                goatDoor2 = 3;
                // door3Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));

                break;

            case 3:
                //car is in door 3
                door3.setDoorContents(2);
                carDoor = 3;
                //door 2 is a goat
                door1.setDoorContents(1);
                goatDoor1 = 1;

                //door 3 is a goat
                door2.setDoorContents(1);
                goatDoor2 = 2;

                break;
        }

        System.out.println("Door 1: "+door1.getDoorContents());
        System.out.println("Door 2: "+door2.getDoorContents());
        System.out.println("Door 3: "+door3.getDoorContents());

    }

    public int[] getGoats (){

        int [] goats = {goatDoor1,goatDoor2};
        return goats;
    }


    public int getcarDoor() {
        return carDoor;
    }

    public void setSwitchDoor(int switchDoor) {
        this.switchDoor = switchDoor;
    }

    public int getSwitchDoor() {
        return switchDoor;
    }
}
