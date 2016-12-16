#include <Button.h>                           //Cool button library
#include <PinChangeInt.h>                     //Library detects pin changes

Button winButton = Button(1, INPUT);          //"Win" Button wired to pin 1
Button loseButton = Button(5, INPUT);         //"Lose" Button wired to pin 5
uint16_t BEACON_UUID = 0xDEAD;

void setup() {
  Bean.enableConfigSave(false);               //Saves battery life, clears settings
  attachPinChangeInterrupt(1, triggerButtonPress, CHANGE);  //Wakes bean up when button is pressed
  attachPinChangeInterrupt(5, triggerButtonPress, CHANGE);  //Wakes bean up when button is pressed
  Bean.enableAdvertising(false);              //turns off beacon temporarially
  Bean.setBeaconParameters(BEACON_UUID, 0, 0);  //clears UUID
  Bean.enableAdvertising(true);               //turns beacon back on
}

void loop() {
  if(winButton.isPressed()){                  //If "Win" button is pressed,
    Bean.sleep(200);
    if(winButton.isPressed()){
    Bean.enableAdvertising(false);            //turns off beacon
    Bean.setBeaconParameters(BEACON_UUID, 0, 1);  //advertises UUID minor of 1
    Bean.enableAdvertising(true);             //turns on beacon
    Bean.setLed(0, 255, 0);                   //lights up green for a win
    delay(3000);
    Bean.enableAdvertising(false);          
    Bean.setBeaconParameters(BEACON_UUID, 0, 0);    //reverts changes
    Bean.enableAdvertising(true);
    Bean.setLed(0,0,0);
  }
  }
  if(loseButton.isPressed()){                 //If "Lose" button is pressed,
    Bean.sleep(200);
    if(loseButton.isPressed()){
    Bean.enableAdvertising(false);            //much the same as above
    Bean.setBeaconParameters(BEACON_UUID, 0, 2);
    Bean.enableAdvertising(true);
    Bean.setLed(255, 0, 0);                   //red instead
    delay(3000);
    Bean.enableAdvertising(false);
    Bean.setBeaconParameters(BEACON_UUID, 0, 0);
    Bean.enableAdvertising(true);
    Bean.setLed(0,0,0);
  }
  }
Bean.sleep(0xFFFFFFFF);}                    //Put device to sleep if not connected

void triggerButtonPress() {
  
}

