package com.project.senior.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TestActivity extends Activity {//implements Request.RequestCallback {



    //private TestActivityListener listener;
    private Request r;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        EditText et = (EditText)findViewById(R.id.requestText);
        TextView tv = (TextView)findViewById(R.id.textOutput);
        Button searchBtn = (Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(et.getText());
                //r = new Request("United States", "Texas", "Mission", 200, this);
                //r.execute();
            }
        });
    }
}

/*
import android.app.*;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


public class TestActivity extends Fragment
        implements Request.RequestCallback {
    //implement requestHandler callback, so we can get results from the Request Handler pulling data.
    static interface SearchFragmentListener{
        void searchCallBackMethod(String JSONSRESULT);
    }
    private SearchFragmentListener listener;
    private Request h;
    private String json;
    private boolean addFavorites = false;
    private ProgressBar progressBar;

    public TestActivity() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (SearchFragmentListener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View layout = inflater.inflate(R.layout.activity_test,container,false);

        //this.progressBar = (ProgressBar)layout.findViewById(R.id.progressbar);

        //This is apart of the fragments core-functionality and therefore does not need to be passed to listener.
        ((Button)layout.findViewById(R.id.searchBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WARNING: View view is null because of this not being an activity!!!!!!
                onClickSearchButton(layout); // We need to pass layout - as this is the actual view that we get from the inflater!
            }
        });
        return layout;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    //we dont need to pass this off to the listener because this is the fragments core-functionality
    public void onClickSearchButton(View view) {
        //create a RequestHandler, which will get the data from the Server and send it to the Results.java activity.
        if (h != null && h.getStatus().equals(AsyncTask.Status.RUNNING)) //if a search is already in progress, we shouldn't trigger another one.
            return;
        if(this.progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
        }
        /*
        h = new Request(
                ((TextView) view.findViewById(R.id.country)).getText().toString(),
                ((TextView) view.findViewById(R.id.state)).getText().toString(),
                ((TextView) view.findViewById(R.id.city)).getText().toString(),
                200,
                this);

        h = new Request("United States", "Texas", "Mcallen", 200, this);
        h.execute();
    }



    @Override
    public void Callback(String JSONResult) {
        if(this.progressBar!=null){
            progressBar.setVisibility(View.INVISIBLE);
        }
        if(listener!=null) { //if we have a listener it means that there is a god like creator that wants us to do whatever they want!
            listener.searchCallBackMethod(JSONResult);
        }
    }

}
*/