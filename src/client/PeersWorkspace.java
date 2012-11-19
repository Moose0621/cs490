package client;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public class PeersWorkspace implements Composite {

   /**
    * Create the composite.
    * 
    * @param parent
    * @param style
    */
   public PeersWorkspace(Composite parent, int style) {
      super();

   }

   protected void checkSubclass() {
      // Disable the check that prevents subclassing of SWT components
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.awt.Composite#createContext(java.awt.image.ColorModel, java.awt.image.ColorModel,
    * java.awt.RenderingHints)
    */
   @Override
   public CompositeContext createContext(ColorModel arg0, ColorModel arg1, RenderingHints arg2) {
      // TODO Auto-generated method stub
      return null;
   }

}
