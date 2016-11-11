#include <Button.h>                           //Cool button library

Button winButton = Button(1, INPUT);          //"Win" Button wired to pin 1
Button loseButton = Button(5, INPUT);         //"Lose" Button wired to pin 5

void setup() {
  // put your setup code here, to run once:
Bean.enableWakeOnConnect(true);               //Wakes up if something tries to connect
Bean.enablePairingPin(true);                  //Lets device connect, pass is 000000
BeanHid.enable();                             //Temp, Lets device act as keyboard
}

void loop() {
  // put your main code here, to run repeatedly:
  bool connected = Bean.getConnectionState(); //Check connection
  if (connected) {
    Bean.setLed(0, 255, 0);                   //Leaves LED on if connected
  if(winButton.isPressed()){                  //If "Win" button is pressed,
    BeanHid.sendKeys("Win");                  //Send the word "Win"
  }
  if(loseButton.isPressed()){                 //If "Lose" button is pressed,
    BeanHid.sendKeys("Loss");                 //Send the word "Loss"
  }
  }
else {Bean.setLed(0, 0, 0);                   //Turn LED off if not connected
Bean.sleep(0xFFFFFFFF);}                      //Put device to sleep if not connected
  }
