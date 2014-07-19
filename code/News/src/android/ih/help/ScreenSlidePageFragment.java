package android.ih.help;

import android.content.Intent;
import android.ih.news.ArticleListActivity;
import android.ih.news.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ScreenSlidePageFragment extends Fragment {
	
	private int m_num;
	
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
        
        switch (pos) {
		case 1:
			iv.setImageResource(R.drawable.help_page1);
			
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
        
        return rootView;
    }
}
