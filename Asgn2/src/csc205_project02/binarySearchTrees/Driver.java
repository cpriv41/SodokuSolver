package csc205_project02.binarySearchTrees;

import bridges.base.Color;
import bridges.connect.Bridges;

public class Driver
{
	private static Color colorBlue;
	private static Color colorRed;
	private static Color colorGreen;
	
	public static void main ( String[] args )
	{
		System.out.println ( "Hello world, from CSC 205 Project 2");

		Bridges<String, Student> bridges = 
				new Bridges<String, Student>(2, "924406789604", "CalebPrivitera");

		bridges.setTitle("CSC 205 - Project 2");
		
		colorBlue = new Color();    colorBlue.setColor("blue");
		colorRed = new Color();     colorRed.setColor("red");		
		colorGreen = new Color();   colorGreen.setColor("green");

		/** create a binary search tree to test the tree methods */
		BinarySearchTree_Bridges<String, Student> myTree = new BinarySearchTree_Bridges<String, Student> ();
		myTree = buildTree();
		displayTheTree(bridges, myTree);
		
		/** set all the nodes in the tree to be blue */
		myTree.resetColors( colorBlue );
		displayTheTree(bridges, myTree);
			
		/** search for a given target key, highlighting the nodes on the search path */
		myTree.get(colorRed, "george");
		displayTheTree(bridges, myTree);
		
		/** find and highlight the path to the smallest node in the tree */
		myTree.resetColors( colorBlue );
		myTree.first(colorRed);
		displayTheTree(bridges, myTree);	
		
		/** find and highlight the path to the largest node in the tree */
		myTree.resetColors( colorBlue );
		myTree.last(colorRed);
		displayTheTree(bridges, myTree);
		
		/** find and highlight all the values with the given major */
		myTree.resetColors( colorBlue );		
		int numMajors = myTree.countAndColorMajors(colorRed, "ART");
		displayTheTree(bridges, myTree);

		/** find and highlight all values within a given range */
		myTree.resetColors( colorBlue );		
		myTree.colorRange(colorRed, "evan", "keith");
		displayTheTree(bridges, myTree);
		
		/** find and highlight all values within a given range */	
		myTree.resetColors( colorBlue );			
		myTree.colorRange(colorRed, "amy", "gian");
		displayTheTree(bridges, myTree);
		
		/** delete the "small" keys from the tree */
		myTree.resetColors( colorBlue );	
		myTree.deleteSmall( b.getName() );
		displayTheTree(bridges, myTree);	
		
		/** delete the "small" keys from the tree */
		myTree = buildTree();
		myTree.resetColors( colorBlue );
		myTree.deleteSmall( f.getName() );
	

		displayTheTree(bridges, myTree);	
		
		/** delete the "large" keys from the tree */
		myTree = buildTree();
		myTree.resetColors( colorBlue );
		myTree.deleteLarge( "gian" );
		displayTheTree(bridges, myTree);			
		
		/** delete the "large" keys from the tree */
		myTree = buildTree();
		myTree.resetColors( colorBlue );
		myTree.deleteLarge( "keith" );


		displayTheTree(bridges, myTree);
		
		/** highlight the inorder predecessor of the given key */
		myTree.resetColors( colorBlue );
		myTree.colorLower( e.getName(), colorRed);
		displayTheTree(bridges, myTree);
		
		/** highlight the inorder predecessor of the given key */
		myTree.resetColors( colorBlue );	
		myTree.colorLower( m.getName(), colorRed);
		displayTheTree(bridges, myTree);
		
		/** highlight the inorder successor of the given key */
		myTree.resetColors( colorBlue );	
		myTree.colorHigher( d.getName(), colorRed);
		displayTheTree(bridges, myTree);
		
		/** highlight the inorder successor of the given key */
		myTree.resetColors( colorBlue );	
		myTree.colorHigher( i.getName(), colorRed);
		displayTheTree(bridges, myTree);
		
		System.out.println ( "Good-bye world, from CSC 205 Project 2");
	}

	
	private static Student a = new Student ( "alice", "ART" );
	private static Student b = new Student ( "bob", "BIO" );
	private static Student c = new Student ( "chad", "CHM" );
	private static Student d = new Student ( "dan", "DNS" );
	private static Student e = new Student ( "edith", "ENL" );
	private static Student f = new Student ( "fran", "ART" );
	private static Student g = new Student ( "george", "GEO" );
	private static Student h = new Student ( "hal", "HST" );
	private static Student i = new Student ( "ian", "ART" );
	private static Student j = new Student ( "joe", "JUS" );
	private static Student k = new Student ( "kate", "BIO" );
	private static Student l = new Student ( "lara", "LAW" );
	private static Student m = new Student ( "matt", "MTH" );
	
	private static BinarySearchTree_Bridges <String, Student>  buildTree( )  {
		
		/** create 13 nodes and link them together to form a tree */
		BinarySearchTree_Bridges<String, Student> aLeaf = new BinarySearchTree_Bridges<String, Student>(a.getName(), a, null, null);
		BinarySearchTree_Bridges<String, Student> eLeaf = new BinarySearchTree_Bridges<String, Student>(e.getName(), e, null, null);
		BinarySearchTree_Bridges<String, Student> gLeaf = new BinarySearchTree_Bridges<String, Student>(g.getName(), g, null, null);
		BinarySearchTree_Bridges<String, Student> jLeaf = new BinarySearchTree_Bridges<String, Student>(j.getName(), j, null, null);
		BinarySearchTree_Bridges<String, Student> lLeaf = new BinarySearchTree_Bridges<String, Student>(l.getName(), l, null, null);
			
		BinarySearchTree_Bridges<String, Student> bTree = new BinarySearchTree_Bridges<String, Student>(b.getName(), b, aLeaf, null);
		BinarySearchTree_Bridges<String, Student> fTree = new BinarySearchTree_Bridges<String, Student>(f.getName(), f, eLeaf, gLeaf);
		BinarySearchTree_Bridges<String, Student> hTree = new BinarySearchTree_Bridges<String, Student>(h.getName(), h, fTree, null);
		BinarySearchTree_Bridges<String, Student> kTree = new BinarySearchTree_Bridges<String, Student>(k.getName(), k, jLeaf, lLeaf);
		BinarySearchTree_Bridges<String, Student> mTree = new BinarySearchTree_Bridges<String, Student>(m.getName(), m, kTree, null);
		BinarySearchTree_Bridges<String, Student> iTree = new BinarySearchTree_Bridges<String, Student>(i.getName(), i, hTree, mTree);
		BinarySearchTree_Bridges<String, Student> dTree = new BinarySearchTree_Bridges<String, Student>(d.getName(), d, null, iTree);
		BinarySearchTree_Bridges<String, Student> cTree = new BinarySearchTree_Bridges<String, Student>(c.getName(), c, bTree, dTree);
				
		/** color all the nodes in the tree */
		aLeaf.root.getVisualizer().setColor(colorGreen);
		eLeaf.root.getVisualizer().setColor(colorGreen);
		gLeaf.root.getVisualizer().setColor(colorGreen);
		jLeaf.root.getVisualizer().setColor(colorGreen);
		lLeaf.root.getVisualizer().setColor(colorGreen);
		bTree.root.getVisualizer().setColor(colorGreen);
		fTree.root.getVisualizer().setColor(colorGreen);
		hTree.root.getVisualizer().setColor(colorGreen);
		kTree.root.getVisualizer().setColor(colorGreen);
		mTree.root.getVisualizer().setColor(colorGreen);
		iTree.root.getVisualizer().setColor(colorGreen);
		dTree.root.getVisualizer().setColor(colorGreen);
		cTree.root.getVisualizer().setColor(colorGreen);
	
		return( cTree );
	}  

	/** generate a URL to see a visualization of the tree */
	private static void displayTheTree (
			Bridges<String, Student> bridges, BinarySearchTree_Bridges<String, Student> myTree) {

		if ( myTree != null ) {
			bridges.setDataStructure(myTree.root);
			bridges.visualize();
		}
	}
}
