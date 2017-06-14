import java.awt.Color;
import javax.swing.*;

class Cell extends JButton {

  private final int cellX;
  private final int cellY;
  public String color;

  private Cell(){
    super();

    this.cellX = 0;
    this.cellY = 0;
    this.color = "WHITE";
  }

  public Cell(int cellX, int cellY){
    super();
    this.cellX = cellX;
    this.cellY = cellY;
    this.color = "WHITE";
  }

  public int getCellX(){
    return this.cellX;
  }

  public int getCellY(){
    return this.cellY;
  }

  public void setRED(){
    this.setBackground(Color.RED);
    this.color = "RED";
    this.setEnabled(false);
  }

  public void setYELLOW(){
    this.setBackground(Color.YELLOW);
    this.color = "YELLOW";
    this.setEnabled(true);
  }
  public void setWHITE(){
    this.setBackground(Color.WHITE);
    this.color = "WHITE";
    this.setEnabled(false);
  }

  public void setBLUE(){
    this.setBackground(Color.BLUE);
    this.color = "BLUE";
    this.setEnabled(false);
  }
}
