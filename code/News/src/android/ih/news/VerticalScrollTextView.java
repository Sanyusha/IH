package android.ih.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.TextView;

public class VerticalScrollTextView extends TextView {
    private float step =0f;  
    private Paint mPaint;
    private String text;
    private float width;
    private List<String> textList = new ArrayList<String>();    //textview?

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);        
    }
    

    public VerticalScrollTextView(Context context) {
        super(context);        
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {        
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);   
              final int widthMode = MeasureSpec.getMode(widthMeasureSpec);  
        if (widthMode != MeasureSpec.EXACTLY) {   
            throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }      
      
        float length = 0;        
        if(text==null|text.length()==0){
                return ;
        }      
        
            //?textview?

        textList.clear();
        
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<text.length();i++){
            Log.e("textviewscroll",""+i+text.charAt(i));
            if(length<width){
                builder.append(text.charAt(i));
                length += mPaint.measureText(text.substring(i, i+1));
                if(i==text.length()-1){
                    Log.e("textviewscroll",""+i+text.charAt(i));
                    textList.add(builder.toString());
                }
            }else{
                textList.add(builder.toString().substring(0,builder.toString().length()-1));
            builder.delete(0, builder.length()-1) ;
            length= mPaint.measureText(text.substring(i, i+1));
            i--;
            }
            
        }
    }


	@Override
	protected void onDraw(Canvas canvas) {
		if (textList.size()==0)  {
			return;
			}
		for (int i = 0; i < textList.size(); i++) {
            canvas.drawText(textList.get(i), 0, this.getHeight()+(i+1)*mPaint.getTextSize()-step, getPaint());
        }
            
	        invalidate();    
	        step = step+0.3f;
	        if (step >= this.getHeight()+textList.size()*mPaint.getTextSize()) {
	            step = 0;
	        }     
	}
    
    

}
