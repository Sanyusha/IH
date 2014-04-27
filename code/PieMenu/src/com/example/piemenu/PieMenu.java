package com.example.piemenu;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.images.CircleBitmap;
import com.example.images.DownloadImagesTask;

public class PieMenu extends View{
	
	private final static int BIG_CIRCLE_RADIUS = 400;
	private final static int SMALL_CIRCLE_RADIUS = 400;
	// update
	private Paint smallCircleCore;
	private Paint lineCore;
	private Paint bigArc;
	private Paint bigArcDraw;
	private Paint pencil;
	private Paint middleCircleBody;
	private Paint arcTouchedBack;
	private Paint circlePaint;
	private Paint subCirclePaint;
	
	public Bitmap myBmp;
	public Bitmap[] circleBmp = new Bitmap[3];
	
	private int initial, midInitial;
	private int finalangle;
	private int middleCircleRadius;
	private int i =0;

	private int centerCircleradius;

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


	private RectF oval = new RectF();
	private RectF middleOval = new RectF();
	private RectF finalOVal = new RectF();

	private Context context;
	private int px, py;

	private boolean isClicked = true;
	private boolean[] arcTouched= new boolean[] {false, false, false, false, false, false, false, false, false, false};
	
	EmbossMaskFilter emboss;
	EmbossMaskFilter forBig;

	private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
	
	public int SOKOL = 0;
	
	private BasicTree<AnnotatedImage> menu;
	private ActionListener menuSelectedListener;
	
	public PieMenu(Context context) {
		super(context);
		this.context = context;
		initSmallCircle();

		// TODO Auto-generated constructor stub
	}
	
	/*
	public PieMenu(Context context, ActionListener menuSelectedListener) {
		super(context);
		
		this.menu = new BasicTree<AnnotatedImage>(null); // root data is null, it's children are the categories
		this.menuSelectedListener = menuSelectedListener;
	}
	*/
	
	public PieMenu(Context context, AttributeSet attri)
	{
		super(context, attri);
		this.context = context;

		initSmallCircle();
	}

	public PieMenu(Context context, AttributeSet attri, int defaultStyle)
	{
		super(context, attri, defaultStyle);
		this.context = context;

		initSmallCircle();
	}

	private void initSmallCircle()
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

		bigArc.setColor(0xFF424242);
		bigArc.setStyle(Paint.Style.FILL);

		bigArcDraw = new Paint(smallCircleCore);
		bigArcDraw.setStrokeWidth(4);
		bigArcDraw.setColor(0xFF000000);

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
		
		arcTouchedBack = new Paint(Paint.ANTI_ALIAS_FLAG);
		arcTouchedBack.setColor(Color.WHITE);
		arcTouchedBack.setStyle(Paint.Style.FILL);      

		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeWidth(20);                  //the width of the outermost circle


		float[] direection = new float[]{1,1,1};
		float light = 0.4f;
		float specualr = 6;
		float blur = 3.5f;      

		emboss = new EmbossMaskFilter(direection, light, specualr, blur);
		forBig = new EmbossMaskFilter(direection, 1f, 4, 2.5f);
		
		bitmap.clear();
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.thinker));
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.globe));
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.soccer));
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.graph));      
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.horos));
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.weather));
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.culture));
		bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.airplane));
		//bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.love));      
		//bitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.judaism));
		
		//myBmp = BitmapFactory.decodeResource(getResources(), R.drawable.love);
		circleBmp[0] = null;
		circleBmp[1] = null;
		circleBmp[2] = null;
		
		middleCircleBody.setMaskFilter(forBig);
		bigArc.setMaskFilter(forBig);

		String log="";
		Log.d(log, "Value of px & py " + getMeasuredWidth() + " " + getMeasuredHeight());
	}

	//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	//    {
	//        int measuredWidth = measure(widthMeasureSpec);
	//        int measuredHeight = measure(heightMeasureSpec);
	//
	//        int d = Math.min(measuredWidth, measuredHeight);
	//
	//        setMeasuredDimension(d,d);
	//    }

	private int measure(int measureSpec)
	{
		int result = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if(specMode == MeasureSpec.UNSPECIFIED)
		{
			result = 200;
		}
		else
		{
			result = specSize;
		}

		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		px = getMeasuredWidth()/2;
		py = getMeasuredHeight()/2;

		Log.d("Sokol", px + ":::" + py );

		initial = 144;
		finalangle = 252;

		centerCircleradius  = 30; // ME
		middleCircleRadius = 400; // the radius of the big circle

		int init, fina;
		init = 180;
		fina = 540;
		finalOVal.set(px-middleCircleRadius-4, py-middleCircleRadius-4, px+middleCircleRadius+4, py+middleCircleRadius+4);
		middleOval.set(px-middleCircleRadius, py-middleCircleRadius, px+middleCircleRadius, py+middleCircleRadius);
		Log.d("sokol", (px-middleCircleRadius) + ":::" + (py-middleCircleRadius) + ":::" + (px+middleCircleRadius) + ":::" + (py+middleCircleRadius));

		/*
		while(init<fina)
		{
			circlePaint.setColor(colors[i]);
			canvas.drawArc(finalOVal,init,10,false, circlePaint);
			i++;
			if(i>=colors.length)
			{
				i=0;
			}
			init = init + 10;

		}
		*/
		
		circlePaint.setColor(Color.WHITE);
		canvas.drawArc(finalOVal,init,360,false, circlePaint);
		
		midInitial = 180;

		i=0;

		// Creating the arcs
		for (int j = 0; j < 8; j++) {
			if(arcTouched[j]) {
				for (int k = 0; k < 3; k++) {
					double[] subCircleCenter = getSubCircleCenter(j, 3, k);
					
					String[] strUrl = new String[3];
					strUrl[0] = "http://cdn.theatlantic.com/static/infocus/2012yip120612/s_y01_00000001.jpg";
					strUrl[1] = "http://www.israelhayom.co.il/sites/default/files/styles/125x125/public/images/articles/2014/02/28/13935419672126_b.jpg";
					strUrl[2] = "http://www.israelhayom.co.il/sites/default/files/styles/566x349/public/images/articles/2014/02/28/13935420094025_b.jpg";
					
					//ImageView imgView = new ImageView(getContext());
					
					//imgView.setTag(url1);
					Bitmap bmp1, bmp2, bmp3;
					
					
					if (circleBmp[k] == null) {
						new DownloadImagesTask(this, k).execute(strUrl[k]);
					}
					
					
//					String sLog;
//					sLog = "Sokol";
//					if (imgView == null) {
//						sLog = "barca";
//					} else {
//						sLog = imgView.toString();
//					}
//					
//					Log.d("fd", sLog);
					//bmp1 = ((BitmapDrawable)imgView.getDrawable()).getBitmap();
					
					
					int targetWidth = (int) subCircleCenter[2];
				    int targetHeight = (int) subCircleCenter[2];
				    
				    /*
				    circleBmp[k] = CircleBitmap.getCroppedBitmap(circleBmp[k]);
				    int R = (int) subCircleCenter[2] +40;
				    float min = Math.min(circleBmp[k].getHeight(), circleBmp[k].getWidth());
				    
				    
				    canvas.drawBitmap(
				    		circleBmp[k],
					        new Rect(0, 0, circleBmp[k].getWidth(), circleBmp[k].getHeight()),
					        new Rect((int)subCircleCenter[0]-R, (int)subCircleCenter[1]-R, 
					        		circleBmp[k].getWidth() / ((int)min / R) + (int)subCircleCenter[0]-R, 
					        		circleBmp[k].getHeight() / ((int)min / R) + (int)subCircleCenter[1]-R),
					        null);
					*/       
				    /*
					canvas.drawBitmap(
			    	CircleBitmap.getCroppedBitmap(myBmp),
			    	 null, (putBitmapTo(200, 36, 450, 100, 100)), null);
			       */
				    
				    
				    if (circleBmp[k] == null) {
				    	Log.d("circleBmp[k]", "Null");
				    	canvas.drawCircle((int) subCircleCenter[0], (int) subCircleCenter[1], (int) subCircleCenter[2], subCirclePaint);
				    } else {
				    	Log.d("circleBmp[k]", "Not null");
				    	circleBmp[k] = CircleBitmap.getCroppedBitmap(circleBmp[k]);
					    int R = (int) subCircleCenter[2] + 20;
					    float min = Math.min(circleBmp[k].getHeight(), circleBmp[k].getWidth());
					    
					    
					    canvas.drawBitmap(
					    		circleBmp[k],
						        new Rect(0, 0, circleBmp[k].getWidth(), circleBmp[k].getHeight()),
						        new Rect((int)subCircleCenter[0]-R, (int)subCircleCenter[1]-R, 
						        		circleBmp[k].getWidth() / ((int)min / R) + (int)subCircleCenter[0]-R, 
						        		circleBmp[k].getHeight() / ((int)min / R) + (int)subCircleCenter[1]-R),
						        null);
				    }
				}
				
				canvas.drawArc(middleOval, midInitial, 45, true, arcTouchedBack);
				canvas.drawArc(middleOval, midInitial, 45, true, pencil);
			}
			else {
				canvas.drawArc(middleOval, midInitial, 45, true, middleCircleBody);
				canvas.drawArc(middleOval, midInitial, 45, true, pencil);
			}
			
			canvas.drawBitmap(bitmap.get(j), null, (putBitmapTo(midInitial, 45, 450, px, py)), null); // ME
			//canvas.drawBitmap(bitmap.get(0), null, (putBitmapTo(midInitial, 36, 450, px, py)), null); // ME
			
			Log.d("SOKOL", ""+SOKOL);
			
			SOKOL = 0;
			
			midInitial+=45;
		}

		
		/*
		canvas.drawCircle(px, py-10, 40, pencil);
		canvas.drawCircle(px, py-10, 39, smallCircleCore);
		*/
		
		
		//canvas.drawCircle(px, py-10, 35, bigArc);
		canvas.drawCircle(px, py, 10, smallCircleCore);
		
		/*
		canvas.drawCircle(px, py-10, 15, bigArc);
		canvas.drawLine(px-8, py-10, px+8, py-10, lineCore);
		*/
		
		canvas.save();
	}


	private double[] getSubCircleCenter(int sectorNum , int numOfCircles, int circleIndex) {
		int newRadius = middleCircleRadius + 100;
		int angle = sectorNum * 45;
		int deltaAngle = 45 / numOfCircles;
		angle += ((circleIndex) * deltaAngle) + (deltaAngle/2);
		
		double rAngle = Math.toRadians(angle);
		
		double x, y;
		x = newRadius * Math.cos(rAngle);
		y = newRadius * Math.sin(rAngle);
		
		double subCircleX = px - x;
		double subCircleY = py - y;
		double subRadius = Math.PI*(middleCircleRadius+100) / (10*numOfCircles);
		double[] subCircleCenter = {px-x , py-y , subRadius};
		
		Log.d("subCircle", "x:::" + subCircleX + "y:::" + subCircleY);
		return subCircleCenter;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		float centerX = px;
		float centerY = (py-centerCircleradius+(centerCircleradius/4));

		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			if(isInSideCenterCircle(centerX, centerY, centerCircleradius, event.getX(), event.getY()))
			{
				return super.onTouchEvent(event);
			}

			for(int i = 0 ; i < 10; i++){
				if(isInArc(180 + (i*36), 36, middleCircleRadius, px, py, event.getX(), event.getY()))
				{
					makeAllFalse();
					arcTouched[i] = true;
					invalidate();
					return true;
				}
			}


		}
		case MotionEvent.ACTION_UP:
		{
			if(isInSideCenterCircle(centerX, centerY, centerCircleradius, event.getX(), event.getY()))
			{
				return super.onTouchEvent(event);
			}

			for(int i=0 ; i<10; i++){

				if(isInArc(180 + (36*i), 36, middleCircleRadius, px, py, event.getX(), event.getY()))
				{
					Toast.makeText(getContext(), "In the " +i+ "'th" +" Arc !!!", Toast.LENGTH_SHORT).show();
					makeAllFalse();

					invalidate();
					return false;
				}
			}
			makeAllFalse();
			invalidate();
			return super.onTouchEvent(event);
		}

		case MotionEvent.ACTION_MOVE:
		{
			if(isInSideCenterCircle(centerX, centerY, centerCircleradius, event.getX(), event.getY()))
			{
				makeAllFalse();
				invalidate();
				return super.onTouchEvent(event);
			}

			for(int i = 0 ; i<10 ;i++){
				if(isInArc(180 + 36*i, 36, middleCircleRadius, px, py, event.getX(), event.getY()))
				{
					makeAllFalse();
					arcTouched[i] = true;
					invalidate();
					return false;
				}
			}

			makeAllFalse();
			invalidate();
			return super.onTouchEvent(event);
		}
		}
		return super.onTouchEvent(event);
	}

	private boolean isInSideCenterCircle(float centerX, float centerY, float radius, float co_ordinateX, float co_ordinateY)
	{       
		return ((Math.pow((centerX-co_ordinateX),2))+(Math.pow((centerY-co_ordinateY),2))-Math.pow(radius, 2))<=0;      
	}

	private void changeIsClicked()
	{
		isClicked =(isClicked?(false):(true));
	}

	private RectF putBitmapTo(int startAngle, int SweepAngle, float radius, float centerX, float centerY)
	{
		float locx = (float) (centerX +((radius/17*11)*Math.cos(Math.toRadians(
				(startAngle+SweepAngle+startAngle)/2
				))
				));
		float locy = (float) (centerY + ((radius/17*11)*Math.sin(Math.toRadians(
				(startAngle+SweepAngle+startAngle)/2
				))
				));

		return new RectF(locx - 70, locy - 70, locx + 70, locy + 70); // ME   

	}

	private boolean isInArc(int startAngle, int SweepAngle, float radius, float centerX, float centerY, float toCheckX, float toCheckY)
	{

		double degree;

		if((Math.pow(centerX - toCheckX, 2) + Math.pow(centerY - toCheckY, 2) -
				Math.pow(radius, 2))<=0){           			
			double radian = Math.atan((centerY-toCheckY)/(centerX-toCheckX));

			degree = Math.toDegrees(radian);
			if (degree < 0) {
				degree+= 90;
			}

			int quarter = detectQuarter(centerX, centerY, toCheckX, toCheckY);
			degree += 90 + (90 * quarter);

			//Log.d("radian", ""+radian);
			//Log.d("angles", degree + ":::" + startAngle + "::" + SweepAngle);

			return (degree >= startAngle) && (degree <= startAngle + SweepAngle);           
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

	private void makeAllFalse()
	{
		for(int i=0;i<arcTouched.length;i++)
		{
			arcTouched[i] = false;
		}
	}
}

