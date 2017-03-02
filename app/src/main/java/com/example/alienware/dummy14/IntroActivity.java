package com.example.alienware.dummy14;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Alienware on 30-05-2016.
 */




//this activity will be the first ie launch activity
//with checker it will launch intro slides if first time launch
//notification bar transparent, view pager for the the slides and inflting all slides
//listener for click on next and click button

public class IntroActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout linearLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button skip, next;
    private Checker checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check for first time launch by using class checker
        checker = new Checker(this);
        if (!checker.isFirst()) {
            launchHome();
            finish();
        }


        //to make the notification bar transparent for the intro slides
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.intro);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        linearLayout = (LinearLayout) findViewById(R.id.DotsLayout);
        skip = (Button) findViewById(R.id.SkipButton);
        next = (Button) findViewById(R.id.NextButton);


        //layout of intro slides in array
        layouts = new int[]{
                R.layout.intro1,
                R.layout.intro2,
                R.layout.intro3,
                R.layout.intro4
        };

        //adding bottom dots
        addDots(0);
        //turning notification bar transparent
        changeStatusbarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        //on click listener for skip and next

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHome();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we check if current slide is less than total no of slides if yes next slide else launchHome
                int curr = getItem(+1);
                if (curr < layouts.length) {
                    viewPager.setCurrentItem(curr); //next slide
                } else {
                    launchHome();
                }
            }
        });


    }
        //adding dots to slide according to slide
        public void addDots(int currPage){
        dots = new TextView[layouts.length];
        int[] activecolors = getResources().getIntArray(R.array.active);
        int[] inactivecolors = getResources().getIntArray(R.array.inactive);
        linearLayout.removeAllViews();
        for(int i = 0; i< dots.length; i++){
            dots[i]= new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(25);
            dots[i].setTextColor(inactivecolors[currPage]);
            linearLayout.addView(dots[i]);
        }
        if(dots.length > 0)
            dots[currPage].setTextColor(activecolors[currPage]);
         }


    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }


    private void launchHome() {
        checker.setFirst(false);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }


    //change listener for view pager
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            //if last page next button should display text got it and skip = invisible
            if(position == layouts.length -1){
                next.setText(getString(R.string.got_it));
                skip.setVisibility(View.GONE);
            }else {
                next.setText(getString(R.string.next));
                skip.setVisibility(View.VISIBLE);
            }

        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


//making notification bar transparent for intro slides
    private void changeStatusbarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public class MyViewPagerAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;
        public MyViewPagerAdapter(){
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            View view = (View) object;
            container.removeView(view);
        }

    }

}


