package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  TorpedoStore primary = mock(TorpedoStore.class);
  TorpedoStore secondary = mock(TorpedoStore.class);

  @BeforeEach
  public void init(){

    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void firstFire(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void alternatingFire(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    //when(primary.isEmpty()).thenReturn(false);
    //when(secondary.isEmpty()).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);

    boolean secondresult = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, secondresult);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void emptyFire(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);

    Boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void emptyFireOther(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    Boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    when(secondary.isEmpty()).thenReturn(true);

    Boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, secondResult);

    verify(primary, times(2)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void failedFire(){
    when(primary.fire(1)).thenReturn(false);

    Boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Fail(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);

    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }


  @Test
  public void emptyFireOtherEmpty(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    Boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    when(secondary.isEmpty()).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);

    Boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void emptyFireEmpty(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    Boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireAllTests(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(true);

    Boolean result = ship.fireTorpedo(FiringMode.ALL);

    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    Boolean secondresult = ship.fireTorpedo(FiringMode.ALL);

    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);

    Boolean thirdresult = ship.fireTorpedo(FiringMode.ALL);

    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireAllinsideFail(){
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(false);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    Boolean result = ship.fireTorpedo(FiringMode.ALL);

    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(false);

    Boolean secondresult = ship.fireTorpedo(FiringMode.ALL);

    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    Boolean thirdresult = ship.fireTorpedo(FiringMode.ALL);

    verify(primary, times(3)).fire(1);
    verify(secondary, times(1)).fire(1);
  }
}
