package sigh;
 
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
 
/**
 * The {@code Input} class is a utilities class that provides easy and simple to use keyboard and touch input.
 *
 * <p>
 * This class is constant, there may only ever be a single instance of it that can be accessed statically.
 * To setup this class within your game, in the frame creation of your Application, write this line of code :
 * <blockquote><pre>
 * {@code Input.setup(component); }
 * </pre></blockquote>
 * Where component is your JPanel or JFrame or Canvas.
 * <p>
 * The final setup step is to add this line of code to the 'update' method of your game. For most users,
 * this will be your main game loop.
 * <blockquote><pre>
 * Input.update();
 * </pre></blockquote>
 * <p>
 * Now you are all setup and ready to accept input. Here are some examples of how:
 * <blockquote><pre>
 * if (Input.getKey(Key.W)) {
 *    //Do something <b>while</b> 'W' is being pressed
 * }
 *
 * if (Input.getMouseDown(Input.LEFT_MOUSE_BUTTON)) {
 *    //Do something <b>when</b> the left mouse button is pressed
 * }
 *
 * if (Input.getMouseUp(Input.RIGHT_MOUSE_BUTTON)) {
 *    //Do something <b>when</b> the right mouse button is released
 * }
 * </pre></blockquote>
 * <em>* Note that these examples should be inside your 'update' method</em>
 *
 * @author Leon Montealegre
 * @version 0.03
 */
public final class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
   
    public static final int LEFT_MOUSE_BUTTON = 0;
    public static final int RIGHT_MOUSE_BUTTON = 2;
    public static final int MIDDLE_MOUSE_BUTTON = 1;
   
    /** Instance of Input to attach as the input listener. */
    private static final Input instance = new Input();
   
    /** Array of all the keys that have been pressed for the first time. */
    private static final boolean[] firstPressedKeys = new boolean[255];
   
    /** Array of all the keys that are being held. */
    private static final boolean[] pressedKeys = new boolean[255];
   
    /** Array of all the keys that have been released. */
    private static final boolean[] releasedKeys = new boolean[255];
   
    /** Array of all the mouse buttons that have been pressed for the first time. */
    private static final boolean[] mouseFirstPressed = new boolean[3];
   
    /** Array of all the mouse buttons that are being held. */
    private static final boolean[] mousePressed = new boolean[3];
   
    /** Array of all the mouse buttons that have been released. */
    private static final boolean[] mouseReleased = new boolean[3];
   
    /** The current position of the mouse. */
    public static Point mousePosition = new Point();
   
    private static ArrayList<Boolean> valuesToChange = new ArrayList<Boolean>();
    private static ArrayList<Integer> keysToChange = new ArrayList<Integer>();
    private static ArrayList<Boolean> mouseValuesToChange = new ArrayList<Boolean>();
    private static ArrayList<Integer> mouseButtonsToChange = new ArrayList<Integer>();
    private static int scrollAmount = 0;
    private static Key lastKeyPressed = null;
   
    private Input() {}
   
    /**
     * Sets up the given AWT component with the input.
     *
     * @param   component
     *          The AWT component to attach the input to.
     *
     * @throws  NullPointerException
     *          If {@code component} is null.
     */
    public static void setup(Component component) {
        component.addKeyListener(instance);
        component.addMouseListener(instance);
        component.addMouseMotionListener(instance);
        component.addMouseWheelListener(instance);
    }
   
    /**
     * Gets the last key that was pressed.
     *
     * @return  A {@code Key} representing the last key to be pressed during
     *          this 'frame', or {@code null} if no key has been
     *          pressed this 'frame'.
     */
    public static Key getAnyKeyDown() {
        return lastKeyPressed;
    }
   
    /**
     * Gets if a key was pressed for the first time after being released.
     *
     * @param   key
     *          The key to check for.
     *
     * @return  {@code true} if the key was pressed for the first time after being released,
     *          {@code false} otherwise.
     *
     * @throws  NullPointerException
     *          If {@code key} is null.
     */
    public static boolean getKeyDown(Key key) {
        if (firstPressedKeys[key.keyValue]) {
            if (!keysToChange.contains(key.keyValue)) {
                valuesToChange.add(true);
                keysToChange.add(key.keyValue);
            }
            return true;
        }
        return false;
    }
   
    /**
     * Gets if a key is being held.
     *
     * @param   key
     *          The key to check for.
     * @return  {@code true} if the key is being held,
     *          {@code false} otherwise.
     *
     * @throws  NullPointerException
     *          If {@code key} is null.
     */
    public static boolean getKey(Key key) {
        return pressedKeys[key.keyValue];
    }
   
    /**
     * Gets if a key was released.
     *
     * @param   key
     *          The key to check for.
     *
     * @return  {@code true} if they key was released,
     *          {@code false} otherwise.
     *
     * @throws  NullPointerException
     *          If {@code key} is null.
     */
    public static boolean getKeyUp(Key key) {
        if (releasedKeys[key.keyValue]) {
            if (!keysToChange.contains(key.keyValue)) {
                valuesToChange.add(false);
                keysToChange.add(key.keyValue);
            }
            return true;
        }
        return false;
    }
   
    /**
     * Gets if a mouse button was pressed for the first time.
     *
     * @param   button
     *          The mouse button to check for.
     *
     * @return  {@code true} if the mouse button was pressed for the first time,
     *          {@code false} otherwise.
     *
     * @throws  MouseButtonOutOfBoundsException
     *          If {@code button} is either negative or greater or equal to 3
     */
    public static boolean getMouseDown(int button) {
        if (button < 0 || button >= 3)
            throw new MouseButtonOutOfBoundsException(button);
       
        if (mouseFirstPressed[button]) {
            if (!mouseButtonsToChange.contains(button)) {
                mouseValuesToChange.add(true);
                mouseButtonsToChange.add(button);
            }
            return true;
        }
        return false;
    }
   
    /**
     * Gets if a mouse button is being held.
     *
     * @param   button
     *          The mouse button to check for.
     *
     * @return  {@code true} if the mouse button is being held,
     *          {@code false} otherwise.
     *
     * @throws  MouseButtonOutOfBoundsException
     *          If {@code button} is either negative or greater or equal to 3
     */
    public static boolean getMouse(int button) {
        if (button < 0 || button >= 3)
            throw new MouseButtonOutOfBoundsException(button);
       
        return mousePressed[button];
    }
   
    /**
     * Gets if a mouse button was released.
     *
     * @param   button
     *          The mouse button to check for.
     *
     * @return  {@code true} if the mouse button was released,
     *          {@code false} otherwise.
     *
     * @throws  MouseButtonOutOfBoundsException
     *          If {@code button} is either negative or greater or equal to 3
     */
    public static boolean getMouseUp(int button) {
        if (button < 0 || button >= 3)
            throw new MouseButtonOutOfBoundsException(button);
       
        if (mouseReleased[button]) {
            if (!mouseButtonsToChange.contains(button)) {
                mouseValuesToChange.add(false);
                mouseButtonsToChange.add(button);
            }
            return true;
        }
        return false;
    }
   
    /**
     * @return  {@code true} if the mouse was scrolled up,
     *          {@code false} otherwise.
     */
    public boolean scrolledUp() {
        return scrollAmount == -1;
    }
   
    /**
     * @return  {@code true} if the mouse was scrolled down,
     *          {@code false} otherwise.
     */
    public boolean scrolledDown() {
        return scrollAmount == 1;
    }
   
    /** Must be called every 'frame' of the application so that it can reset the proper variables. */
    public static void update() {
        lastKeyPressed = null;
        scrollAmount = 0;
       
        for (int i=0; i<keysToChange.size(); i++) {
            boolean firstPressed = valuesToChange.get(i);
            int key = keysToChange.get(i);
            if (firstPressed)
                firstPressedKeys[key] = false;
            else
                releasedKeys[key] = false;
        }
        keysToChange.clear();
        valuesToChange.clear();
       
        for (int i=0; i<mouseButtonsToChange.size(); i++) {
            boolean firstPressed = mouseValuesToChange.get(i);
            int button = mouseButtonsToChange.get(i);
            if (firstPressed)
                mouseFirstPressed[button] = false;
            else
                mouseReleased[button] = false;
        }
        mouseButtonsToChange.clear();
        mouseValuesToChange.clear();
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void keyPressed(KeyEvent e) {
        lastKeyPressed = Key.getKey(e.getKeyCode());
        this.updateKeys(e.getKeyCode(), true);
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void keyReleased(KeyEvent e) {
        this.updateKeys(e.getKeyCode(), false);
    }
   
    /** Helper method for key input. */
    private void updateKeys(int code, boolean pressed) {
        if (code < 255) {
            if (!pressedKeys[code])
                firstPressedKeys[code] = pressed;
            pressedKeys[code] = pressed;
            releasedKeys[code] = !pressed;
        }
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void mousePressed(MouseEvent e) {
        this.updateMouse(e.getButton()-1, true);
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.updateMouse(e.getButton()-1, false);
    }
   
    /** Helper method for mouse input */
    private void updateMouse(int button, boolean pressed) {
        if (!mousePressed[button])
            mouseFirstPressed[button] = pressed;
        mousePressed[button] = pressed;
        mouseReleased[button] = !pressed;
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void mouseDragged(MouseEvent e) {
        Input.mousePosition = new Point(e.getX(), e.getY());
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void mouseMoved(MouseEvent e) {
        Input.mousePosition = new Point(e.getX(), e.getY());
    }
   
    /** NOT FOR PUBLIC USE */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollAmount = e.getWheelRotation();
    }
   
    /** NOT FOR PUBLIC USE */
    public void mouseClicked(MouseEvent e) {}
   
    /** NOT FOR PUBLIC USE */
    public void mouseEntered(MouseEvent e) {}
   
    /** NOT FOR PUBLIC USE */
    public void mouseExited(MouseEvent e) {}
   
    /** NOT FOR PUBLIC USE */
    public void keyTyped(KeyEvent e) {}
   
}