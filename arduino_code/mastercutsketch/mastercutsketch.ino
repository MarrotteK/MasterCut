#include <Button.h>

Button winButton = Button(1, INPUT);
Button loseButton = Button(5, INPUT);

void setup() {
  // put your setup code here, to run once:
Bean.enableWakeOnConnect(true);
Bean.enablePairingPin(true);
pinMode(13,OUTPUT);
BeanHid.enable();
}

void loop() {
  // put your main code here, to run repeatedly:
  bool connected = Bean.getConnectionState();
  if (connected) {
    Bean.setLed(0, 255, 0);
  if(winButton.isPressed()){
    BeanHid.sendKeys("Win");
  }
  if(loseButton.isPressed()){
    BeanHid.sendKeys("Loss");
  }
  }
else {Bean.setLed(0, 0, 0);
Bean.sleep(0xFFFFFFFF);}
  }
