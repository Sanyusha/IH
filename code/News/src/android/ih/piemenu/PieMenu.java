package android.ih.piemenu;

// TO DO
// THERE IS A PROBLEM WITH THE SIZE OF BITMAPS
// WHEN THE SIZE EXCEEDS SOME VALUE JAVA.LANG.OUTOFMEMORYECEPTION IS BEEN THROWNED

// SHOULD BE IMAGES OF THE FIRST LEVEL ALSO CROPPED AS CIRCLES ???

import java.net.URL;
import java.util.ArrayList;
import java.util.List;




import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.ih.news.CategoryFragment;
import android.ih.news.CategoryListActivity;
import android.ih.news.R;
import android.ih.news.StartActivity;
import android.ih.news.model.AnnotatedImage;
import android.ih.news.model.Article;
import android.ih.news.model.AnnotatedImage.ImageSize;
import android.ih.piemenu.PieMenuItem;
import android.ih.piemenu.BasicTree.Node;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.SystemClock;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.app.Dialog;

public class PieMenu extends View{
	
	private Dialog dlg;
	
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

	
	private static BasicTree<PieMenuItem> menu = new BasicTree<PieMenuItem>(null);
	
	private static int GENERAL_PADDING = 50;
	
	private static PieMenuItem selectedCategory = null;
	private static UUID selectedArticle = null;
	
	/**
	 * Please lock before change!
	 * @return the menu
	 */
	public static BasicTree<PieMenuItem> getMenu() {
		return menu;	
	}
	
	//////////////////////////////////
	
	private int MAIN_CIRCLE_RADIUS;
	private int BIG_CIRCLE_RADIUS;
	private int NEXT_LEVEL_RADIUS;
	private static final int BASIC_ARC_ANGLE = 45;
	
	private int categoryTextX, categoryTextY;
	private int articleRectX1, articleRectY1, articleRectX2, articleRectY2;
	
	private float touchX = -1, touchY = -1;
	
	private Paint touchedArea, notTouchedArea;
	private TextPaint categoryText, arcText;
	private TextPaint articleTextPaint;
	
	private TestPieMenuItem threeDotsImg, backImg, mainImg;

	private Node<PieMenuItem> contNode;
	
	private boolean showPie = false;
	
	private int level1FirstNode = 0, level1NodeCount;
	
	private double ARC_TEXT_PARAM = 0.9;
	private MotionEvent ev;
	
	public PieMenu(Context context, AttributeSet attri)
	{
		super(context, attri);
		
		//this.context = context;

		initPie();
		
		//DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		
		menu.makeTreeNotTouched(menu.getRoot());
		
		// Obtain MotionEvent object
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis() + 100;
		float x = 0.0f;
		float y = 0.0f;
		// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
		int metaState = 0;
		MotionEvent motionEvent = MotionEvent.obtain(
		    downTime, 
		    eventTime, 
		    MotionEvent.ACTION_UP, 
		    x, 
		    y, 
		    metaState
		);

		// Dispatch touch event to view
		//dispatchTouchEvent(motionEvent);
		//this.onTouchEvent(motionEvent);
		motionEvent.recycle();
	}
	
	public void setEv(MotionEvent ev) {
		this.ev = ev;
		this.onTouchEvent(ev);
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
		
		categoryText = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		categoryText.setColor(Color.WHITE); 
		categoryText.setTextSize(60);
		categoryText.setTextAlign(Paint.Align.LEFT);
		categoryText.setTypeface(Typeface.DEFAULT_BOLD);
		
		articleTextPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
		articleTextPaint.setColor(Color.BLACK);
		articleTextPaint.setTextSize(40);
		articleTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		
		arcText = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		arcText.setColor(Color.BLACK); 
		arcText.setTextSize(40);
		//arcText.setStyle(Paint.Style.FILL_AND_STROKE);
		arcText.setTextAlign(Paint.Align.CENTER);
		
		level1NodeCount = (360 / BASIC_ARC_ANGLE) - 1;
		
		threeDotsImg = new TestPieMenuItem();
		threeDotsImg.setImage(new AnnotatedImage("three dots image", "local",
					BitmapFactory.decodeResource(getResources(), R.drawable.three_dots), ImageSize.PIE));
		threeDotsImg.setTitle("Next...");
		
		mainImg = new TestPieMenuItem();
		mainImg.setImage(new AnnotatedImage("three dots image", "local",
					BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), ImageSize.PIE));
		mainImg.setTitle("Main...");
		
		backImg = new TestPieMenuItem();
		backImg.setImage(new AnnotatedImage("three dots image", "local",
					BitmapFactory.decodeResource(getResources(), R.drawable.back), ImageSize.PIE));
		backImg.setTitle("Back...");
		
		contNode = new Node<PieMenuItem>(threeDotsImg);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		showPie = false;
		
		initValues();
		
		drawFirstLevel(canvas);		
		
		canvas.save();
		
		//Toast.makeText(getContext(), "Show pie:::" + showPie, Toast.LENGTH_SHORT).show();
		
		checkClickOutsidePie();
	}
	
	private void checkClickOutsidePie() {
		if (touchX != -1 && touchY != -1 && !showPie) {
			menu.makeTreeNotTouched(menu.getRoot());
			//contNode.setTouched(false);
			//touchX = -1;
			//touchY = -1;
			invalidate();
		}
	}
	
	public void setDlg(Dialog dlg) {
		this.dlg = dlg;
	}
	
	public static PieMenuItem getSelectedCategory() {
		return selectedCategory;
	}
	
	public static UUID getSelectedArticle() {
		return selectedArticle;
	}
	
	/**
	 * Define the central point of the pie
	 */
	private void initValues() {
		px = getMeasuredWidth() / 2;
		py = getMeasuredHeight() / 2;
		
		BIG_CIRCLE_RADIUS = (Math.min(px, py) * 6) / 10;
		
		MAIN_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS / 5;
		
		NEXT_LEVEL_RADIUS = BIG_CIRCLE_RADIUS  * 3 / 7;
		
		categoryTextX = getMeasuredWidth();
		categoryTextY = py + (BIG_CIRCLE_RADIUS * 5 / 3);
		
		articleRectX1 = GENERAL_PADDING;
		articleRectY1 = getMeasuredWidth() * 5 / 100;
		articleRectX2 = getMeasuredWidth() * 17 / 21;
		articleRectY2 = py - (BIG_CIRCLE_RADIUS * 5 / 3);
	}
	
	private void drawFirstLevel(Canvas canvas) {
		Bitmap bm;
		Rect r;
		int rSide, m = 10;
		Node<PieMenuItem> menuRoot;
		
		menuRoot = menu.getRoot();
		
		selectedCategory = null;
		selectedArticle = null;
		
		drawChildren(canvas, menuRoot, 1, 180, 540);
		
		if (menuRoot.getTouched()) {
			canvas.drawCircle(px, py, MAIN_CIRCLE_RADIUS, touchedArea);
		} else {
			canvas.drawCircle(px, py, MAIN_CIRCLE_RADIUS, notTouchedArea);
		}
		//canvas.drawCircle(px, py, MAIN_CIRCLE_RADIUS, smallCircleCore);
		
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
		Bitmap nodeImg = null;
		
		if (nai == null) return;
		//Log.d("drawNode", "null");
		//Log.d("drawNode", nai.getData().getTitle() + ":::" + nai.getParent().getTouched());
		if (nai != contNode && !nai.getParent().getTouched() && level > 1) return;
		//Log.d("drawNode", "2 line");
		if (level < 1) return;
		//Log.d("drawNode", "level");
		
		ovalRadius = calculateRadiusByLevel(level);
		
		oval = new RectF();
		oval.set(px - ovalRadius, py - ovalRadius, px + ovalRadius, py + ovalRadius);
		
		if (nai.getTouched()) {
			if (level == 1) {
				selectedCategory = nai.getData();
			}
			
			if (level == 2) {
				selectedArticle = nai.getData().getId();
			}
			
			canvas.drawArc(oval, startAngle, angle, true, touchedArea);
		} else {
			canvas.drawArc(oval, startAngle, angle, true, notTouchedArea);
		}
		
		if (((nai.getData().getImage() != null))) {
			if (nai.getData().getImage().getImage() != null) {
				nodeImg = nai.getData().getImage().getImage();
			}
		}
		
		drawAdditionalInfo(canvas, nai, level, nai.getTouched(), nodeNum, nodeCount, nodeImg);
		
		canvas.drawArc(oval, startAngle, angle, false, circlePaint);
			
		if (nodeImg != null) {
			drawTheArcBitmap(canvas, nodeImg, ovalRadius, nodeNum, nodeCount, startAngle, endAngle);
		} else {
			drawTextInArc(canvas, nai.getData().getTitle(), oval, startAngle, angle);
		}
	}
	
	private void drawTextInArc(Canvas canvas, String text, RectF oval, int startAngle, int angle) {
		//double rAngle = getRadianAngle(startAngle);
		//float newRadius = (float) (ARC_TEXT_PARAM * radius);
		int hOffset = 0, vOffSet = 50;
		Path path = new Path();
		
		path.addArc(oval, startAngle, angle);

		canvas.drawTextOnPath(text, path, hOffset, vOffSet, arcText);
	}
	
	private void drawAdditionalInfo(Canvas canvas, Node<PieMenuItem> nai, int level, boolean touched,
									int nodeNum, int nodeCount, Bitmap nodeImg) {
		String title;
		//int ovalRadius;
		
		//ovalRadius = calculateRadiusByLevel(level);
		
		//if (!nai.getTouched()) return;
		if (nai.getData().getTitle() == null) return;
		
		title = nai.getData().getTitle();
		
		switch (level) {
		case 1:
			if (touched) {
				drawLevel1AddInfo(canvas, title);
			}
			
			break;
		case 2:
			//if (touched) selectedArticle = nai.getData().getUrl();
			
			drawLevel2AddInfo(canvas, title, nodeNum, nodeCount, nodeImg, touched);
			
			break;
		}	
	}
	
	private void drawLevel1AddInfo(Canvas canvas, String title) {
		Path path = new Path();
		path.moveTo(GENERAL_PADDING, categoryTextY);
		path.lineTo(categoryTextX, categoryTextY);
		
		canvas.drawTextOnPath(title, path, 0, 0, categoryText);
	}
	
	private void drawLevel2AddInfo(Canvas canvas, String title, int nodeNum, int nodeCount, Bitmap nodeImg, Boolean touched) {
		int y1, y2;
		int rectHeight;
		int imgPadding;
		RectF rect;
		
		rectHeight = (articleRectY2 - articleRectY1) / nodeCount;
		
		y1 = articleRectY1 + (nodeNum * (GENERAL_PADDING / 2)) + (nodeNum * rectHeight);
		y2 = y1 + rectHeight;
		
		// draw the frame rectangle
		rect = new RectF(articleRectX1, y1, articleRectX2 + rectHeight, y2);
		if (touched) {
			canvas.drawRect(rect, touchedArea);
		} else {
			canvas.drawRect(rect, notTouchedArea);
		}
		
		// draw the text
		imgPadding = rectHeight / 10;
		
		rect = new RectF(articleRectX1, y1 + imgPadding, articleRectX2 - GENERAL_PADDING, y2);
		
		StaticLayout sl = new StaticLayout(title, articleTextPaint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);

		canvas.save();
		canvas.translate(rect.left, rect.top);
		sl.draw(canvas);
		canvas.restore();
		
		// draw the bitmap
		//rectHeight = rectHeight * 8 / 10;
		
		rect = new RectF(articleRectX2 + imgPadding, y1 + imgPadding, articleRectX2 + rectHeight - imgPadding, y2 - imgPadding);
		
		if (nodeImg != null) {
			canvas.drawBitmap(nodeImg, null, rect, null);
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
		
		//Log.d("drawChildren", nai.getData().getTitle() + ":::" + l.size());
		
		if (l.size() != 0) {
			//if (level == 2) selectedArticle = null;
			
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
					Log.d("drawChildren", nai.getData().getTitle() + ":::" + l.size());
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
			if (!menu.getRoot().getTouched()) {
				menu.makeTreeNotTouched(menu.getRoot());
				contNode.setTouched(false);
				menu.getRoot().setTouched(true);
				if (level1FirstNode > 0) level1FirstNode -= level1NodeCount;
				Log.d("getLevelTouched", level1FirstNode + ":::" + level1NodeCount);
				
				//touchX = -1; touchY = -1;
				
			}
			
			showPie = true;
			
			return;
		}
		
		for (int i = getFirstNode(level); i < l.size(); i++) {
			Log.d("getLevelTouched", "i:::" + i);
			
			sAngle = startAngle + (sectorNum * angle);
			eAngle = sAngle + angle;
			
			if (isInArc(sAngle, angle, level, px, py, touchX, touchY, true)) {
			// if this sector is for the "three dots" node
				if (i < (l.size() - 1) && eAngle > (endAngle - angle)) {
						if (!contNode.getTouched()) {
							Log.d("getLevelTouched", "TouchedI:::" + i);
							menu.makeLevelNotTouched(l.get(0));
							menu.getRoot().setTouched(false);
							contNode.setTouched(true);
							//showPie = true;
							level1FirstNode = i;
							//touchX = -1;
							//touchY = -1;
							return;
						}
						
						showPie = true;
				}
				else {
					if (l.get(i).getParent().getTouched() || level == 1) {
						menu.makeLevelNotTouched(l.get(i));
						menu.getRoot().setTouched(false);
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
	 * transform to radian angle and go from their "strange" coordinates system
	 * that starts from 180 on the left side and goes to 540 degrees...
	 */
	private double getRadianAngle(int startAngle) {
		return Math.toRadians(360 + (-1 * startAngle));
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
		
		rAngle = getRadianAngle(startAngle);
		
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
				//Log.d("onTouchEvent_DOWN", "X:::" + event.getX() + "Y:::" + event.getY());
				touchX = event.getX();
				touchY = event.getY();
				//selectedCategory = null;
				//selectedArticle = null;
				invalidate();
				break;
			}
			
			case MotionEvent.ACTION_UP: {
				//Log.d("onTouchEvent_UP", "X:::" + event.getX() + "Y:::" + event.getY() + selectedCategory + ":::" + selectedArticle);
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						
						Log.d("onTouchEvent_UP", "X:::" + event.getX() + "Y:::" + event.getY() + selectedCategory + ":::" + getSelectedArticle());
						
						dlg.dismiss();
						if (selectedArticle != null) {
							StartActivity.startArticleActivity(dlg.getContext(), getSelectedArticle());
						} else {
							Intent i = new Intent(getContext(), CategoryListActivity.class);
						
							getContext().startActivity(i);
						}
				break;
			}
			
			case MotionEvent.ACTION_MOVE: {
				//Log.d("onTouchEvent_MOVE", "touchX:::" + touchX + "touchY:::" + touchY + "X:::" + event.getX() + "Y:::" + event.getY());
				//return false;
				if ((touchX != event.getX() || touchY != event.getY()) && 
						(touchX != -1 && touchY != -1)) {
					Log.d("onTouchEvent_MOVE", "touchX:::" + touchX + "touchY:::" + touchY);
					touchX = event.getX();
					touchY = event.getY();
					//selectedCategory = null;
					//selectedArticle = null;
					invalidate();
				}
				
				//touchX = event.getX();
				//touchY = event.getY();
				
				break;
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
			//menu.getRoot().setTouched(true);
			return true;
		} else {
			//menu.getRoot().setTouched(false);
			return false;
		}
	}
	
	
}

