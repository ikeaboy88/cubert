package cubeview3d;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Cube3DView extends Applet {

	public BranchGroup createSceneGraph() {
  // Create the root of the branch graph
  BranchGroup objRoot = new BranchGroup();

  // rotate object has composited transformation matrix
  Transform3D rotate = new Transform3D();
  Transform3D tempRotate = new Transform3D();

        rotate.rotX(Math.PI/4.0d);
  tempRotate.rotY(Math.PI/5.0d);
        rotate.mul(tempRotate);

  TransformGroup objRotate = new TransformGroup(rotate);

  objRoot.addChild(objRotate);
  objRotate.addChild(new ColorCube(0.4));
  // Let Java 3D perform optimizations on this scene graph.
        objRoot.compile();

  return objRoot;
    } // end of CreateSceneGraph method of HelloJava3Db

    // Create a simple scene and attach it to the virtual universe

    public Cube3DView() {
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(null);
        add("Center", canvas3D);

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

  // This will move the ViewPlatform back a bit so the
  // objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    } // end of HelloJava3Db (constructor)
    //  The following allows this to be run as an application
    //  as well as an applet

    public static void main(String[] args) {
  Frame frame = new MainFrame(new Cube3DView(), 256, 256);
    } // end of main (method of HelloJava3Db)
}
