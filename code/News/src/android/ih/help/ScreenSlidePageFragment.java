package android.ih.help;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.ih.news.ArticleListActivity;
import android.ih.news.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
	
	public static final String PREF_DONT_SHOW_HELPER = "dontShowHelper";
	public static final String CHOICE_PREF = "checkPref";
	
	public static ScreenSlidePageFragment newInstance(int position) {
		ScreenSlidePageFragment pageFragment = new ScreenSlidePageFragment();
	    Bundle bundle = new Bundle();
	    bundle.putInt("position", position);
	    pageFragment.setArguments(bundle);

	    return pageFragment;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	int pos = getArguments().getInt("position");
    	
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        
        ImageView iv = (ImageView) rootView.findViewById(R.id.imgBG);
        TextView tv = (TextView) rootView.findViewById(R.id.textView2);
        
        switch (pos) {
		case 0:
			iv.setImageResource(R.drawable.help_page3);
			tv.setText(R.string.help_3);
			break;
		case 1:
			iv.setImageResource(R.drawable.help_page2);
			tv.setText(R.string.help_2);
			break;
		case 2:
			iv.setImageResource(R.drawable.help_page1);
			tv.setText(R.string.help_1);
			break;
		default:
			break;
		}
        
        Button btnRunApp = (Button) rootView.findViewById(R.id.btnRunApp);
        
        btnRunApp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ArticleListActivity.class);
				startActivity(i);
				
				getActivity().finish();
			}
		});
        
        CheckBox dontShowAgain = (CheckBox) rootView.findViewById(R.id.dontShowAgain);
        dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					SharedPreferences prefs = getActivity().getSharedPreferences(CHOICE_PREF, Context.MODE_PRIVATE);
					prefs.edit().putBoolean(PREF_DONT_SHOW_HELPER, true).commit();
					//Intent i = new Intent(getActivity(), SplashScreen.class);
					//i.putExtra(SplashScreen.DONT_SHOW_HELPER_SCREEN_INDEX, true);
					
				}
			}
		});

        return rootView;
    }
}
