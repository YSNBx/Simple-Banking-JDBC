package Behavior;

public class MenuController {
    private MenuInterface menuInterface;

    public void execute() {
        this.menuInterface.start();
    }

    public void setMenuInterface(MenuInterface menuInterface) {
        this.menuInterface = menuInterface;
    }

}
