package android.ih.piemenu;

// TO DO
// THERE IS A PROBLEM WITH THE SIZE OF BITMAPS
// WHEN THE SIZE EXCEEDS SOME VALUE JAVA.LANG.OUTOFMEMORYECEPTION IS BEEN THROWNED

// SHOULD BE IMAGES OF THE FIRST LEVEL ALSO CROPPED AS CIRCLES ???

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.ih.news.R;
import android.ih.news.model.AnnotatedImage;
import android.ih.piemenu.PieMenuItem;
import android.ih.piemenu.BasicTree.Node;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PieMenu extends View{
	
	// update
	private Paint smallCircleCore;
	private Paint lineCore;
	private Paint bigArc;
	private Paint pencil;
	private Paint middleCircleBody;
	private Paint circlePaint;
	private Paint subCirclePaint;
	
	public Bitmap myBmp;
	public Bitmap[] circleBmp = new Bitmap[3];

	int[] colors = new int[]{0xFF33B5E5,
			0xFFAA66CC,
			0xFF99CC00,
			0xFFFFBB33,
			0xFFFF4444,
			0xFF0099CC,
			0xFFAA66CC,
			0xFF669900,
			0xFFFF8800,
			0xFFCC0000};      //colors of the outermost circle



	
	private int px, py;
	//private Context context;
	
	
	EmbossMaskFilter emboss;
	EmbossMaskFilter forBig;

	public int SOKOL = 0;
	
	private BasicTree<PieMenuItem> menu;
	
	//////////////////////////////////////////////////////////
	private final int MAIN_CIRCLE_RADIUS = 70;
	private final int BIG_CIRCLE_RADIUS = 400;
	private final int NEXT_LEVEL_RADIUS = 200;
	private final int BASIC_ARC_ANGLE = 45;
	
	private float touchX = -1, touchY = -1;
	
	private Paint touchedArea, notTouchedArea;
	private Paint categoryText;
	
	private TestPieMenuItem threeDotsImg, backImg, mainImg;

	private Node<PieMenuItem> contNode;
	
	private boolean showPie = false;
	
	private int level1FirstNode = 0, level1NodeCount;
	
	private void createTree() {
		TestPieMenuItem ai, ai_sub;
		Node<PieMenuItem> nai, nai1;
		
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		
		menu = new BasicTree<PieMenuItem>(ai);
		
		List<Node<PieMenuItem>> ail = new ArrayList<Node<PieMenuItem>>();
		List<Node<PieMenuItem>> ail1;
		List<Node<PieMenuItem>> ail2 = new ArrayList<Node<PieMenuItem>>();
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ai.setTitle("Culture");
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ai.setTitle("Army");
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		
		ail1.add(new Node<PieMenuItem>(ai_sub));
		
		nai1 = new Node<PieMenuItem>(ai_sub);
		ail2.add(new Node<PieMenuItem>(ai_sub));
		ail2.add(new Node<PieMenuItem>(ai_sub));
		ail2.add(new Node<PieMenuItem>(ai_sub));
		nai1.setChildren(ail2);
		
		ail1.add(nai1);
		
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ai.setTitle("Politics");
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));;
		ai.setTitle("Something");
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail1 = new ArrayList<Node<PieMenuItem>>();
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		ai_sub = new TestPieMenuItem();
		ai_sub.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.globe)));
		ail1.add(new Node<PieMenuItem>(ai_sub));
		nai.setChildren(ail1);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail.add(nai);
		
		ai = new TestPieMenuItem();
		ai.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		nai = new Node<PieMenuItem>(ai);
		ail.add(nai);
		
		menu.getRoot().setChildren(ail);
	}
	
	public PieMenu(Context context, AttributeSet attri)
	{
		super(context, attri);
		
		//this.context = context;

		initPie();
		
		createTree();
		
		//DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
	    
		Toast.makeText(getContext(), "Tree created", Toast.LENGTH_SHORT).show();
	}
	
	private void initPie()
	{
		setFocusable(true);
		
		smallCircleCore = new Paint(Paint.ANTI_ALIAS_FLAG);
		lineCore = new Paint(Paint.ANTI_ALIAS_FLAG);
		bigArc = new Paint(Paint.ANTI_ALIAS_FLAG);
		pencil = new Paint(Paint.ANTI_ALIAS_FLAG);
		subCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		lineCore.setColor(Color.YELLOW);               //draws the small "-" inside
		lineCore.setStyle(Paint.Style.FILL);
		lineCore.setStrokeWidth(4);
		//bigCircleCore.setStrokeWidth(5);            

		smallCircleCore.setStyle(Paint.Style.FILL);
		smallCircleCore.setColor(Color.WHITE);              //draws the two small circles

//		bigArc.setColor(0xFF424242);
//		bigArc.setStyle(Paint.Style.FILL);
//
//		bigArcDraw = new Paint(smallCircleCore);
//		bigArcDraw.setStrokeWidth(4);
//		bigArcDraw.setColor(0xFF000000);

		pencil.setStrokeWidth(0.5f);                          //width of the lines between sectors
		pencil.setColor(Color.WHITE);                        //draws the lines between sectors
		pencil.setStyle(Paint.Style.STROKE);
		
		subCirclePaint.setStrokeWidth(10f);                          //width of the lines between sectors
		subCirclePaint.setColor(Color.WHITE);                        //draws the lines between sectors
		//subCirclePaint.setStyle(Paint.Style.STROKE);
		subCirclePaint.setStyle(Paint.Style.FILL);
		
		//
		middleCircleBody = new Paint(bigArc);
		middleCircleBody.setColor(getResources().getColor(R.color.gray));
		//
		
//		arcTouchedBack = new Paint(Paint.ANTI_ALIAS_FLAG);
//		arcTouchedBack.setColor(Color.WHITE);
//		arcTouchedBack.setStyle(Paint.Style.FILL);      
		
		/////////////////////////////////////////////////////////////////
		touchedArea = new Paint(Paint.ANTI_ALIAS_FLAG);
		touchedArea.setColor(Color.rgb(135, 206, 235)); //SkyBlue
		touchedArea.setStyle(Paint.Style.FILL);
		
		notTouchedArea = new Paint(Paint.ANTI_ALIAS_FLAG);
		notTouchedArea.setColor(Color.WHITE); 
		notTouchedArea.setStyle(Paint.Style.FILL);
		
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeWidth(10);  //the width of the outermost circle
		circlePaint.setColor(Color.GRAY);
		
		categoryText = new Paint(Paint.ANTI_ALIAS_FLAG);
		categoryText.setColor(Color.WHITE); 
		categoryText.setTextSize(40);
		
		level1NodeCount = (360 / BASIC_ARC_ANGLE) - 1;
		
		threeDotsImg = new TestPieMenuItem();
		threeDotsImg.setImage(new AnnotatedImage("three dots image", "local",
					BitmapFactory.decodeResource(getResources(), R.drawable.three_dots)));
		threeDotsImg.setTitle("Next...");
		
		mainImg = new TestPieMenuItem();
		mainImg.setImage(new AnnotatedImage("three dots image", "local",
					BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		mainImg.setTitle("Main...");
		
		backImg = new TestPieMenuItem();
		backImg.setImage(new AnnotatedImage("three dots image", "local",
					BitmapFactory.decodeResource(getResources(), R.drawable.back)));
		backImg.setTitle("Back...");
		
		contNode = new Node<PieMenuItem>(threeDotsImg);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		showPie = false;
		
		setOrigin();
		
		drawFirstLevel(canvas);		
		
		canvas.save();
		
		//Toast.makeText(getContext(), "Show pie:::" + showPie, Toast.LENGTH_SHORT).show();
		
		checkClickOutsidePie();
	}
	
	private void checkClickOutsidePie() {
		if (touchX != -1 && touchY != -1 && !showPie) {
			menu.makeTreeNotTouched(menu.getRoot());
			contNode.setTouched(false);
			touchX = -1;
			touchY = -1;
			invalidate();
		}
	}
	
	/**
	 * Define the central point of the pie
	 */
	private void setOrigin() {
		px = getMeasuredWidth() / 2;
		py = getMeasuredHeight() / 2;
		
		//Log.d("Sokol", px + ":::" + py );
	}
	
	private void drawFirstLevel(Canvas canvas) {
		Bitmap bm;
		Rect r;
		int rSide, m = 10;
		Node<PieMenuItem> menuRoot;
		
		menuRoot = menu.getRoot();
		
		drawChildren(canvas, menuRoot, 1, 180, 540);
		
		canvas.drawCircle(px, py, MAIN_CIRCLE_RADIUS, smallCircleCore);
		
		setMainCircleImg();
		
		if (MAIN_CIRCLE_RADIUS > m) {
			bm = menuRoot.getData().getImage().getImage();
			rSide = MAIN_CIRCLE_RADIUS - m;
			
			r = new Rect(px - rSide, py - rSide,
					px + rSide, py + rSide);
			
			canvas.drawBitmap(bm, null, r, null);
		}
	}
	
	private void setMainCircleImg() {
		if (level1FirstNode > 0) {
			menu.getRoot().setData(backImg);
		} else {
			menu.getRoot().setData(mainImg);
		}
	}
	
	private int calculateRadiusByLevel(int level) {
		return BIG_CIRCLE_RADIUS + (level - 1) * NEXT_LEVEL_RADIUS;
	}
	
	/**
	 * draw a sector on the pie for a specific node
	 */
	private void drawNode(Canvas canvas, Node<PieMenuItem> nai, int level,
					int nodeNum, int nodeCount, int startAngle, int endAngle) {
		RectF oval;
		int ovalRadius, angle = endAngle - startAngle;
		
		if (nai == null) return;
		if (nai != contNode && !nai.getParent().getTouched()) return;
		if (level < 1) return;
		
		ovalRadius = calculateRadiusByLevel(level);
		
		oval = new RectF();
		oval.set(px - ovalRadius, py - ovalRadius, px + ovalRadius, py + ovalRadius);
		
		if (nai.getTouched()) {
			canvas.drawArc(oval, startAngle, angle, true, touchedArea);
			drawAdditionalInfo(canvas, nai, level);
		} else {
			canvas.drawArc(oval, startAngle, angle, true, notTouchedArea);
		}

		canvas.drawArc(oval, startAngle, angle, false, circlePaint);
			
		if (((nai.getData() != null))) {
			drawTheArcBitmap(canvas, nai.getData().getImage().getImage(),
					ovalRadius, nodeNum, nodeCount, startAngle, endAngle);
		}
	}
	
	private void drawAdditionalInfo(Canvas canvas, Node<PieMenuItem> nai, int level) {
		String title;
		int ovalRadius = calculateRadiusByLevel(level);
		
		//if (!nai.getTouched()) return;
		if (nai.getData().getTitle() == null) return;
		
		title = nai.getData().getTitle();
		
		switch (level) {
		case 1:
			canvas.drawText(title, px - ovalRadius, py - ovalRadius, categoryText);
		}	
	}
	
	private void drawTheArcBitmap(Canvas canvas, Bitmap bm, int ovalRadius,
								int nodeNum, int nodeCount, int startAngle, int endAngle) {
		double[] subCircleCenter;
		int R;
		
		subCircleCenter = getSubCircleCenter(nodeNum, nodeCount,
										startAngle, endAngle, ovalRadius);
		
		bm = CircleBitmap.getCroppedBitmap(bm);
		
		R = (int) subCircleCenter[2];
		
	    canvas.drawBitmap(
	    		bm,
		        new Rect(0, 0, bm.getWidth(), bm.getHeight()),
		        new Rect((int) subCircleCenter[0] - R, (int) subCircleCenter[1] - R, 
		        		(int) subCircleCenter[0] + R, (int) subCircleCenter[1] + R),
		        null);
	}
	
	private void drawChildren(Canvas canvas, Node<PieMenuItem> nai, int level,
							 int startAngle, int endAngle) {
		List<Node<PieMenuItem>> l;
		int angle, sAngle, eAngle;
		int sectorNum;
		
		l = nai.getChildren();
		
		if (l.size() != 0) {
			angle = calculateNodeArcAngle(level, startAngle, endAngle, l.size());
			
			getLevelTouched(l, level, startAngle, angle, endAngle);
			
			sectorNum = 0;
			
			for (int i = getFirstNode(level); i < l.size(); i++) {
				sAngle = startAngle + (sectorNum * angle);
				eAngle = sAngle + angle;
				
				if (level > 1 && i == (l.size() - 1) && eAngle < endAngle) eAngle = endAngle;
				
				// if there is more nodes than sectors on the current level 
				// draw "three dots" node
				if (i < (l.size() - 1) && eAngle > (endAngle - angle)) {
					drawNode(canvas, contNode, level, i, l.size(), sAngle, eAngle);
					break;
				} else { // standard case
					drawChildren(canvas, l.get(i), level + 1, sAngle, eAngle);
					
					drawNode(canvas, l.get(i), level, i, l.size(), sAngle, eAngle);
				}
				
				sectorNum++;
			}
		}
	}
	
	private int getFirstNode(int level) {
		if (level == 1) {
			return level1FirstNode;
		} else {
			return 0;
		}
	}
	
	private int calculateNodeArcAngle(int level, int startAngle, int endAngle, int nodeCount) {
		if (level == 1) {
			return BASIC_ARC_ANGLE;
		} else {
			return (Math.abs(endAngle - startAngle)) / nodeCount;
		}
	}
	
	private void getLevelTouched(List<Node<PieMenuItem>> l, int level,
			 					int startAngle, int angle, int endAngle) {
		int sAngle, eAngle;
		int sectorNum = 0;
		
		if (isInMainCircle(px, py, touchX, touchY)) {
			menu.makeTreeNotTouched(menu.getRoot());
			
			if (level1FirstNode > 0) level1FirstNode -= level1NodeCount;
			Log.d("getLevelTouched", level1FirstNode + ":::" + level1NodeCount);
			
			touchX = -1; touchY = -1;
			
			return;
		}
		
		for (int i = getFirstNode(level); i < l.size(); i++) {
			Log.d("getLevelTouched", "i:::" + i);
			
			sAngle = startAngle + (sectorNum * angle);
			eAngle = sAngle + angle;
			
			if (isInArc(sAngle, angle, level, px, py, touchX, touchY, true)) {
			// if this sector is for the "three dots" node
				if (i < (l.size() - 1) && eAngle > (endAngle - angle)) {
						Log.d("getLevelTouched", "TouchedI:::" + i);
						menu.makeLevelNotTouched(l.get(0));
						//contNode.setTouched(true);
						showPie = true;
						level1FirstNode = i;
						touchX = -1;
						touchY = -1;
						return;
				}
				else {
					if (l.get(i).getParent().getTouched()) {
						menu.makeLevelNotTouched(l.get(i));
						
						if (level == 1) contNode.setTouched(false);
						
						l.get(i).setTouched(true);
						showPie = true;
					}
						
					return;
				}
			}
			sectorNum++;
		}
	}
	
	/**
	 * returns the center coordinates and the radius of the incircle
	 * that we want to show in the arc
	 */
	private double[] getSubCircleCenter(int nodeNum, int nodeCount,
										int startAngle, int endAngle, int radius) {
		double r, rAngle, x, y, subCircleX, subCircleY;
		double rFactor = 0.8; // multiply radius by this factor
		double a, c, p, betta; // isosceles triangle triangle dimensions, betta is the angle between two equal sides named 'a'
		double bisector, bisector2;
		int angle = endAngle - startAngle;
		
		startAngle += (angle / 2);
		
		// transform to radian angle and go from their "strange" coordinates system
		// that starts from 180 on the left side and goes to 540 degrees...
		rAngle = Math.toRadians(360 + (-1 * startAngle)); 
		
		a = radius;
		betta = Math.toRadians(angle);
        c = 2 * a * Math.sin(betta / 2);
        p = 0.5 * (2 * a + c);
        
        r = Math.sqrt((p - a) * (p - a) * (p - c) / p); // geometric formula for the radius of the incircle
        
        // the center of the incircle is placed on the bisector
        bisector = a * Math.cos(betta / 2);
        r = r * rFactor;
        bisector2 = bisector - r;
        
        // relative coordinates of the center of the incircle
		x = bisector2 * Math.cos(rAngle); 
		y = bisector2 * Math.sin(rAngle);
		
		// real coordinates of the center of the incircle
		subCircleX = px + x;
		subCircleY = py - y;
		
		double[] subCircleCenter = {subCircleX, subCircleY, r}; ; // return value
		return subCircleCenter; 
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		
		
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				touchX = event.getX();
				touchY = event.getY();
				invalidate();
			}
			
			case MotionEvent.ACTION_UP: {
				
			}

			case MotionEvent.ACTION_MOVE: {
//				touchX = event.getX();
//				touchY = event.getY();
//				invalidate();
			}
		}
	    
	    return true;
	}

	private boolean isInArc(int startAngle, int SweepAngle, int level, 
							float centerX, float centerY, 
							float toCheckX, float toCheckY,
							boolean recursive) {
		double degree;
		float radius = calculateRadiusByLevel(level);
		
		if((Math.pow(centerX - toCheckX, 2) + Math.pow(centerY - toCheckY, 2) -
				Math.pow(radius, 2))<=0) {           			
			double radian = Math.atan((centerY-toCheckY)/(centerX-toCheckX));

			degree = Math.toDegrees(radian);
			if (degree < 0) {
				degree+= 90;
			}

			int quarter = detectQuarter(centerX, centerY, toCheckX, toCheckY);
			degree += 90 + (90 * quarter);

			// recursive part
			if ((degree >= startAngle) && (degree <= startAngle + SweepAngle)) {
				if (recursive && level > 1) {
					return  !isInArc(startAngle, SweepAngle, level - 1, 
							centerX, centerY, toCheckX, toCheckY, false);
				} else {
					return true;
				}
			} else {
				return false;
			}        
		}       
		return false;
	}

	private int detectQuarter(float centerX, float centerY, float toCheckX, float toCheckY) {
		if(toCheckX < centerX){
			if(toCheckY < centerY){
				return 1;
			}
			return 4;
		}
		if(toCheckY < centerY){
			return 2; 
		}
		return 3;
	}
	
	private boolean isInMainCircle(float centerX, float centerY, 
					float toCheckX, float toCheckY) {
		if ((Math.pow(centerX - toCheckX, 2) + Math.pow(centerY - toCheckY, 2) -
				Math.pow(MAIN_CIRCLE_RADIUS, 2)) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	class TestPieMenuItem implements PieMenuItem {
		private AnnotatedImage img;
		private String title;
		
		public void setImage(AnnotatedImage img) {
			this.img = img;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getTitle() { 
			return title;
		}
		
		public AnnotatedImage getImage() {
			return img;
		}
	}
}

