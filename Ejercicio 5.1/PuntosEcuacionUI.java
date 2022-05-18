import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PuntosEcuacionUI extends JFrame{
    // declaracion elemento grafico puntos ecuacion
    private PuntosEcuacion lienzo;
    // declaracion panel global de botones y paneles por seccion
    private JPanel buttons, crear_polinomio, add_termino, graficar;
    // declaracion txt fields
    private JTextField t_grado, t_coef, t_exp, t_linf, t_lsup, t_inc;
    // declaracion buttons
    private JButton b_crear, b_add, b_graficar;
    private Polinomio polinomio;
    
    
    public PuntosEcuacionUI(){
        super("Graficador");
        addWindowListener(new CW());
        setSize(800,550);
        
        // panel global de botones
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(3,0));
        
        // panel seccion crear polinomio
        crear_polinomio = new JPanel();
        crear_polinomio.setLayout(new BorderLayout());
        crear_polinomio.add(new JLabel("Parámetros de la Gráfica"), 
        "North");
        crear_polinomio.add(new JLabel("Grado del polinomio: "),
        "West");
        t_grado = new JTextField(15);
        crear_polinomio.add(t_grado);
        // t_grado accept only numbers
        t_grado.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e){
               char c = e.getKeyChar();
               if (((c < '0') || (c > '9')) && (c !=
               KeyEvent.VK_BACK_SPACE)){
                   e.consume();
               }
           }
        });
        
        b_crear = new JButton("Crear Polinomio");
        crear_polinomio.add(b_crear, "South");
        
        // panel seccion añadir termino
        add_termino = new JPanel();
        add_termino.setLayout(new GridLayout(3,2));
        add_termino.add(new JLabel("Coeficiente del Termino: "),
        "West");
        t_coef = new JTextField();
        add_termino.add(t_coef);
        // t_coef accept only numbers
        t_coef.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e){
               char c = e.getKeyChar();
               if (((c < '0') || (c > '9')) && (c !=
               KeyEvent.VK_BACK_SPACE)){
                   e.consume();
               }
           }
        });
    
        add_termino.add(new JLabel("Exponente del Termino: "),
        "West");
        t_exp = new JTextField();
        add_termino.add(t_exp);
        // t_exp accept only numbers
        t_exp.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e){
               char c = e.getKeyChar();
               if (((c < '0') || (c > '9')) && (c !=
               KeyEvent.VK_BACK_SPACE)){
                   e.consume();
               }
           }
        });
        
        b_add = new JButton("Añadir Termino");
        add_termino.add(b_add, "South");
        
        // panel seccion graficar
        graficar = new JPanel();
        graficar.setLayout(new GridLayout(4,2));
        graficar.add(new JLabel("Límite Inferior: "));
        t_linf = new JTextField();
        graficar.add(t_linf);
        // t_linf accept only numbers
        t_linf.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e){
               char c = e.getKeyChar();
               if (((c < '0') || (c > '9')) && (c !=
               KeyEvent.VK_BACK_SPACE)){
                   e.consume();
               }
           }
        });
        
        graficar.add(new JLabel("Límite Superior: "));
        t_lsup = new JTextField();
        graficar.add(t_lsup);
        // t_lsup accept only numbers
        t_lsup.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e){
               char c = e.getKeyChar();
               if (((c < '0') || (c > '9')) && (c !=
               KeyEvent.VK_BACK_SPACE)){
                   e.consume();
               }
           }
        });
        
        graficar.add(new JLabel("Intervalo: "));
        t_inc = new JTextField();
        graficar.add(t_inc);
        // t_inc accept only numbers
        t_inc.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e){
               char c = e.getKeyChar();
               if (((c < '0') || (c > '9')) && (c !=
               KeyEvent.VK_BACK_SPACE)){
                   e.consume();
               }
           }
        });
        
        b_graficar = new JButton("Graficar");
        graficar.add(b_graficar);
        
        // listeners para botones
        b_crear.addActionListener(new BotonCrear());
        b_add.addActionListener(new BotonAdd());
        b_graficar.addActionListener(new BotonGraficar());
        
        // añadir secciones a seccion global
        buttons.add(crear_polinomio);
        buttons.add(add_termino);
        buttons.add(graficar);
        
        // add lienzo y botones a pantalla
        lienzo = new PuntosEcuacion();
        add(lienzo, "Center");
        add(buttons, "West");
        
        setResizable(false);
        setVisible(true);
    }
    
    // closing window event
    private class CW extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            setVisible(false);
            dispose();
        }
    }
    
    // funcion listener para boton crear polinomio
    private class BotonCrear implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            int grado = Integer.parseInt(t_grado.getText());
            polinomio = new Polinomio(grado);
            
            // dialog creacion polinomio
            JOptionPane.showMessageDialog(null, "Polinomio Creado!");
        }
    }
    
    // funcion listener para boton añadir termino
    private class BotonAdd implements ActionListener{
        public void actionPerformed(ActionEvent e){
            double coeficiente = Double.parseDouble(t_coef.getText());
            int exponente = Integer.parseInt(t_exp.getText());
            
            polinomio.agregaTermino(new Termino(coeficiente,exponente));
            
            t_coef.setText("");
            t_exp.setText("");
        }
    }
    
    // funcion listener para boton graficar polinomio
    private class BotonGraficar implements ActionListener{
        public void actionPerformed(ActionEvent e){
            double inf = Double.parseDouble(t_linf.getText());
            double sup = Double.parseDouble(t_lsup.getText());
            double inc = Double.parseDouble(t_inc.getText());
            
            lienzo.agregaPolinomio(polinomio);
            try
            {
                lienzo.calculaPuntos(inf, sup, inc);
            }
            catch (java.io.IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
    }
    
    // public static void main(String arg[]){
        // PuntosEcuacionUI graficadora = new PuntosEcuacionUI();
    // }
}