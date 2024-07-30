package com.example.movielibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.movielibrary.provider.LoginViewModel;
import com.example.movielibrary.provider.Movie;
import com.example.movielibrary.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{// able to receive the broadcast

    private EditText titleMovie;
    private EditText yearMovie;
    private EditText countryMovie;
    private EditText genreMovie;
    private EditText costMovie;
    private EditText keywordsMovie;
    private EditText movieUSD;
    //private TextView movieNumber;
    private Button buttonClear;

    private String title;
    private int year;
    private String country;
    private String genre;
    private int cost;
    private String keywords;
    private double usd;
    //private int number;

    private SharedPreferences sP;  // save persistence data in any form
    final String KEY_TITLE= "TITLE";  // All the key values
    final String KEY_YEAR= "YEAR";
    final String KEY_COUNTRY= "COUNTRY";
    final String KEY_GENRE= "GENRE";
    final String KEY_COST= "COST";
    final String KEY_KEYWORDS= "KEYWORDS";
    final String KEY_USD= "USD";
    //final String KEY_NUMBER="NUMBER";
    final String FILE_NAME= "data.dat";


    //  Week 5
    private DrawerLayout drawerlayout;      // instance variable
    private NavigationView navigationView;
    Toolbar toolBar;

    ArrayList<String> listItem = new ArrayList<String>();   //    initialise, an array with 0 cell
    ArrayAdapter<String> adapter;           // implement a list of view
    private ListView myListView;

    //  Week 6
    //ArrayList<Item> movie;

    //  Week 7
    private MovieViewModel mMovieViewModel;
    MovieAdapter movieAdapter;

    //  Week 8
    DatabaseReference myRef;
    ArrayList<Movie> data = new ArrayList<>();

    //  Week 10
    View scroll;
    boolean scrollFlag=false;
    float x1=0;
    float x2=0;
    float y1=0;
    float y2=0;

    // Week 11
    GestureDetectorCompat gestureDetector; //engine
    private final int X_MIN = 40;

    //Week 12
    static LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);  // drawer main is the root layout

        ArrayList movie=new ArrayList<>();

//      R = into res directory and id
//      accessing child view whose id is editTextTitle
        titleMovie = findViewById(R.id.editTextTitle);
        yearMovie = findViewById(R.id.editTextYear);
        countryMovie = findViewById(R.id.editTextCountry);
        genreMovie = findViewById(R.id.editTextGenre);
        costMovie = findViewById(R.id.editTextCost);
        keywordsMovie = findViewById(R.id.editTextKeywords);
        //movieNumber = findViewById(R.id.editTextNumber);

//        initialise shared preferences
        sP=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);  //make the file private, can be access by others

        // retrieved data(String) using keys, set default data
        this.title= sP.getString(KEY_TITLE, "");
        this.year= sP.getInt(KEY_YEAR, 0);
        this.country= sP.getString(KEY_COUNTRY, "");
        this.genre= sP.getString(KEY_GENRE, "");
        this.cost= sP.getInt(KEY_COST, 0);
        this.keywords= sP.getString(KEY_KEYWORDS, "");
        //this.number=sP.getInt(KEY_NUMBER,0);

        // Store the data in the blank
        titleMovie.setText(this.title);
        yearMovie.setText(String.valueOf(this.year));   // takes in any parameter
        countryMovie.setText(this.country);
        genreMovie.setText(this.genre);
        costMovie.setText(String.valueOf(this.cost));   // takes in any parameter
        keywordsMovie.setText(this.keywords);
        //movieNumber.setText(String.valueOf(this.number));


        buttonClear=findViewById(R.id.ClearButton);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   // clear all text
                titleMovie.getText().clear();
                yearMovie.getText().clear();
                countryMovie.getText().clear();
                genreMovie.getText().clear();
                costMovie.getText().clear();
                keywordsMovie.getText().clear();

            }
        });

//      Week 4, Task 3
        // listens to messages come from class SMSReceiver
        // Request permissions to access SMS from users
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        // instantiate the broadcast receiver & declaring the broadcast intent
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();


        // Register the broadcast handler that is declared in Receiver class
        registerReceiver(myBroadCastReceiver, new IntentFilter(Receiver.SMS_FILTER));



//        Week 5
        drawerlayout = findViewById(R.id.drawer_layout);  // the whole drawer_main
        navigationView = findViewById(R.id.nav_view);
        toolBar = findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);

//        3 dot at top
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

//        get executed when drawer button is clicked
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.fab);

        // when click the button, add item to the list
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem();
            }
        });

//        takes data from the dataSource and put it inside listView
        myListView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);  // string values
        myListView.setAdapter(adapter);  // notify the listView when it is updated

        Button addItem =findViewById(R.id.AddMovie);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem();
            }
        });


        // initialize
        movieAdapter = new MovieAdapter();
        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        mMovieViewModel.getAllMovie().observe(this, newData -> {
            movieAdapter.setMovie(newData);
            movieAdapter.notifyDataSetChanged();
        });

//        Week 8
        //        Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/moviesTable");

        //Get the ref
        //myRef= FirebaseDatabase.getInstance().getReference();

        //setup the adapter
//        myRef.push().setValue(new Movie(data));

//        movieAdapter = new Movie (this,  data);
//        myRef.push().setAdapter(movieAdapter);
        myRef.push().setValue(new Movie(title, String.valueOf(year), country, genre, String.valueOf(cost), keywords));

//        myRef.push().setValue(listItem);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Movie aMovie = dataSnapshot.getValue(Movie.class);
                Log.d("FIRE_DB", aMovie.getTitle()); // get title of moviesTable
                movieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        // Week 10
//        scroll=findViewById((R.id.scrollView));
//
//        scroll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent e) {
//                float offsetHr = 100, offsetVt = 100;  // beginning value
//
//                switch (e.getActionMasked()){
//                    case MotionEvent.ACTION_DOWN:
//                        x1=(int) e.getX();
//                        y1=(int) e.getY();  //save initial X & Y  // down casting
//                        // happy with the gesture, continue follow up
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        return false;
//                    case MotionEvent.ACTION_UP:
//                        x2=(int) e.getX();
//                        y2=(int) e.getY();  //save second X & Y // down casting
//
//                        // horizontal swipe (left to right)
//                        // math.abs = math absolute value, return positive numbers
//                        if((x2 - x1 > offsetHr) && (Math.abs(y2 - y1) < offsetVt)){
//                            Toast.makeText(getApplicationContext(), "Swipe Horizontally - left to right" , Toast.LENGTH_SHORT).show();
//                            addListItem();  // add a new Movie to the database
//
//                        // vertical swipe (top to bottom)
//                        }else if ((Math.abs(x1 - x2) < offsetHr) && (y2 - y1 > offsetVt)){
//                            Toast.makeText(getApplicationContext(), "Swipe Vertically" , Toast.LENGTH_SHORT).show();
//
//                            // clear all the fields
//                            movieTitle.getText().clear();
//                            movieYear.getText().clear();
//                            movieCountry.getText().clear();
//                            movieGenre.getText().clear();
//                            movieCost.getText().clear();
//                            movieKeywords.getText().clear();
//
//                        // horizontal swipe (right to left)
//                        }else if ((x1 - x2 > offsetHr) && (Math.abs(y2 - y1) < offsetVt)){
//                            Toast.makeText(getApplicationContext(), "Swipe Horizontally - right to left" , Toast.LENGTH_SHORT).show();
//                            addMovieItem();
//
//                        }
//                        return true;
//                    default:
//                        //on the same point, it only print once
//                        //reject the current event, no follow up event
//                        return false;
//                }
//
//            }
//        });

        // Week 11
        // initialize
        MyGestureListener myGestureListener = new MyGestureListener();

        //implementation of callback
        //instance of gesture
        gestureDetector = new GestureDetectorCompat(this, myGestureListener);
        gestureDetector.setOnDoubleTapListener(myGestureListener);

        View layout = findViewById(R.id.scrollView);
        layout.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // takes event from the touch event method and fill it to the engine
        // gestureDetector take series of event together and match with pre define action
        // when detect match action, invoke methods in MyGestureListener()
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        gestureDetector.onTouchEvent(event);

        // false only receive the first event of each gesture
        // engine would not work without it
        return true;
    }


    //  Week 11
    // inherite from one parent class (convenience class)
    // can just override specific methods
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        // void, no other gesture share long press
        @Override
        public void onLongPress(MotionEvent e) {
            // clear all the fields
            titleMovie.getText().clear();
            yearMovie.getText().clear();
            countryMovie.getText().clear();
            genreMovie.getText().clear();
            costMovie.getText().clear();
            keywordsMovie.getText().clear();

        }

        // without consider speed
        // (first event of gesture, current event of gesture, distance btw e2 and previous one)
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int finalYear;
            if (!yearMovie.getText().toString().equals("")) {
                // left to right
                if (distanceX > 0) {
                    finalYear = Integer.parseInt(yearMovie.getText().toString()) + (int) distanceX;
                }
                // right to left
                else {
                    finalYear = Integer.parseInt(yearMovie.getText().toString()) - Math.abs((int) distanceX);
                }
                // Store the data in the blank
                yearMovie.setText(String.valueOf(finalYear));
            }

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            moveTaskToBack(true);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            addMovieItem();
            return true;
        }

        // confirm that there is a single tap
        int count = 0;
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            // Add 150 to the cost
            String cost = costMovie.getText().toString();
            int tempCost = Integer.parseInt(cost);
            String finalCost = String.valueOf(tempCost + 150);

            // Store the data in the blank
            costMovie.setText(finalCost);


            // extra task
            Log.d("tagg", e.getX() + " " + e.getY());

            if (x1< 100 && y1< 100 ) {
                ++count;
            }
            if(x1> 1000 && y2 >400){
                ++count;
                if(count >= 2){

                    // view data gets restored, change the title to all upper cases
                    String lowerCaseTitle= titleMovie.getText().toString().toLowerCase();
                    titleMovie.setText(lowerCaseTitle);  // Store the data in text
                    count = 0;
                }

            }

            return true;

        }
    }



    private void addMovieItem() {
//        set movie data
        this.title= "Spiderman";
        this.year= 2010;
        this.country= "USA";
        this.genre= "Action";
        this.cost=  100;
        this.keywords="Fantasy";

        // Store the data in the blank
        titleMovie.setText(this.title);
        yearMovie.setText(String.valueOf(this.year));   // takes in any parameter
        countryMovie.setText(this.country);
        genreMovie.setText(this.genre);
        costMovie.setText(String.valueOf(this.cost));   // takes in any parameter
        keywordsMovie.setText(this.keywords);

    }

    //     add item to the list
    private void addListItem() {
        EditText titleText = findViewById(R.id.editTextTitle);
        EditText yearText = findViewById(R.id.editTextYear);
        EditText countryText = findViewById(R.id.editTextCountry);
        EditText genreText = findViewById(R.id.editTextGenre);
        EditText costText = findViewById(R.id.editTextCost);
        EditText keywordText = findViewById(R.id.editTextKeywords);

        String title = titleText.getText().toString();
        String year = yearText.getText().toString();
        String country = countryText.getText().toString();
        String genre = genreText.getText().toString();
        String cost = costText.getText().toString();
        String keywords = keywordText.getText().toString();

        listItem.add(title + " | " +year);
        Movie movie = new Movie(title, year, country, genre, cost, keywords); // constructor in class Movie
        mMovieViewModel.insert(movie);
        adapter.notifyDataSetChanged(); // update list


//        SharedPreferences data = getSharedPreferences("movieData", 0);
//        SharedPreferences.Editor editor = data.edit();
//        editor.putString("title", title);
//        editor.putString("year", year);
//        editor.putString("country", country);
//        editor.putString("genre", genre);
//        editor.putString("cost", cost);
//        editor.putString("keyword", keywords);
//        editor.apply();

//        Bundle bundle = new Bundle();
//        //Add your data to bundle
//        bundle.putString("title", title);
//        bundle.putString("year", year);
//        bundle.putString("country", country);
//        bundle.putString("genre", genre);
//        bundle.putString("cost", cost);
//        bundle.putString("keyword", keywords);
//        //Add the bundle to the intent




//        SharedPreferences data = getSharedPreferences("movieData", 0);
//        SharedPreferences.Editor editor = data.edit();
//        editor.putString("title", title);
//        editor.putString("year", year);
//        editor.putString("country", country);
//        editor.putString("genre", genre);
//        editor.putString("cost", cost);
//        editor.putString("keyword", keywords);
//        editor.apply();


//        String myCountry = movieCountry.getText().toString();
//        String myGenre = movieGenre.getText (). toString();
//        String myCost = movieCost.getText(). toString();
//        String myKeywords = movieKeywords.getText().toString();
//        Item i = new Item(myMovie, myYear, myCountry, myGenre, myCost, myKeywords);
//        movie.add(i);
    }

    //    remove the last item from the list
    private void removeLastItem(){
        if(listItem.size()>0){
            listItem.remove(listItem.size()-1);  // remove the last one
            adapter.notifyDataSetChanged();
        }
    }

    //    reset the list item
    private void clearAll(){

        SharedPreferences sp = getSharedPreferences("movieData", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

        // remove all the value in firebase
        myRef.setValue(null);
        listItem.clear();  // reset all
        mMovieViewModel.deleteAll();
        //onChildRemoved();
        adapter.notifyDataSetChanged();
    }

    //    list all movies item
//    Go to new activity
    private void listMovie(){
//        Gson gson = new Gson();
//        Intent intents = new Intent( this, ListMovie.class);
//        String dbStr = gson. toJson(movie);
//        intents.putExtra("movie", dbStr);
//        startActivity(intents);

//        String title = movieTitle.getText().toString();
//        String year = movieYear.getText().toString();
//        String country = movieCountry.getText().toString();
//        String genre = movieGenre.getText().toString();
//        String cost = movieCost.getText().toString();
//        String keywords = movieKeywords.getText().toString();

//        listItem.add(title);
//        listItem.add(year);
//        listItem.add(country);
//        listItem.add(genre);
//        listItem.add(cost);
//        listItem.add(keywords);

        Intent intent = new Intent(getApplicationContext(),ListMovie.class);
        intent.putExtra("arrayList", (ArrayList<String>) listItem);

        startActivity(intent);
        adapter.notifyDataSetChanged();


    }



//  activity occurs in drawer
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.item_id_1) {
                addListItem();     // add movieTitle and movieYear
            } else if (id == R.id.item_id_2) {
                removeLastItem();  // remove last item
            }else if (id == R.id.item_id_3) {
                clearAll();        // reset the list
            }else if (id == R.id.item_id_4) {
                listMovie();       // list movie in cards
            }
            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }


    }

//  Get data from option menu and put in here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }


//   Option Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.clear_Field) {
            // clear all text
            titleMovie.getText().clear();
            yearMovie.getText().clear();
            countryMovie.getText().clear();
            genreMovie.getText().clear();
            costMovie.getText().clear();
            keywordsMovie.getText().clear();
        }else if(id == R.id.Sign_up) {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);

        }else if(id == R.id.Log_in) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

        // Week 5 Extra task
        /*else if(id == R.id.lower_Case_Title) {
            this.title= movieTitle.getText().toString().toLowerCase();
            movieTitle.setText(String.valueOf(this.title));
        }*/
        return true;
    }



//    small message display (pop up message)
//    print out the movie title
    public void titleToast(View view){
        String enteredTextByUser = titleMovie.getText().toString();
        enteredTextByUser = enteredTextByUser.isEmpty() ? "No movie title is added" : "Movie - " + enteredTextByUser + " - has been added";
        Toast.makeText(getApplicationContext(), enteredTextByUser, Toast.LENGTH_LONG).show();

//        increment one when button is pressed
//        movieNumber.setText(String.valueOf(++this.number));

        addListItem(); // add list item

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        get data and add this in temp variable
        this.title= titleMovie.getText().toString();
        this.year= Integer.parseInt(yearMovie.getText().toString());      // String to int
        this.country= countryMovie.getText().toString();
        this.genre= genreMovie.getText().toString();
        this.cost= Integer.parseInt(costMovie.getText().toString());      // String to int
        this.keywords= keywordsMovie.getText().toString();
        // this.number= Integer.parseInt(movieNumber.getText().toString());  // String to int

//        subclass of shared preferences
        SharedPreferences.Editor editor =sP.edit();  // class, edit shared preferences
        editor.putString(KEY_TITLE,this.title);  // change the data in Key
        editor.putInt(KEY_YEAR,this.year);
        editor.putString(KEY_COUNTRY,this.country);
        editor.putString(KEY_GENRE,this.genre);
        editor.putInt(KEY_COST,this.cost);
        editor.putString(KEY_KEYWORDS,this.keywords);
        // editor.putInt(KEY_NUMBER,this.number);

//      async save, can do anything in the background (preferably)
        editor.apply();


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("email", 0);
        String header = sharedPreferences.getString("emailName", "");
        if (!header.equals("")) {
            TextView tv = findViewById(R.id.name);
            tv.setText(header);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }



    @Override
    protected void onRestart() {
        super.onRestart();

    }




//    Task 1

//    don't accept null perameter
//    save data in a key-value format (temporary)
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState); //need this to save and restore the value

        // view data gets saved, you must change the genre to all lower cases
        String lowerCaseGenre= genreMovie.getText().toString().toLowerCase();
        outState.putString("KEY_GENRE", lowerCaseGenre); // put the string in the bundle

    }



//    don't accept null parameter
//    volatile(data get destroy when restart phone
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState) {
        super.onRestoreInstanceState(inState); //need this to save and restore the value

        // view data gets restored, change the title to all upper cases
        String upperCaseTitle= titleMovie.getText().toString().toUpperCase();
        titleMovie.setText(upperCaseTitle);  // Store the data in text
        genreMovie.setText(inState.getString("KEY_GENRE")); // Retrieve data from key and store it in text


    }


    //    extending the BroadcastReceiver class and overriding the onReceive() method
    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {  // executed every time class SMSReceive sends a broadcast

            // Retrieve the message from the intent
            String msg = intent.getStringExtra(Receiver.SMS_KEY);  // get string and pass to the key
            // msg = title; year; Country; Genre; Cost; Keywords


//          parse the incoming message
            StringTokenizer sT = new StringTokenizer(msg, ";");
            // st = [title; year; Country; Genre; Cost; Keywords]
            String theTitle = sT.nextToken();
            String theYear = sT.nextToken();
            String theCountry = sT.nextToken();
            String theGenre = sT.nextToken();
            String theCost = sT.nextToken();
            String theKeywords = sT.nextToken();
            //String theDurationYear = sT.nextToken();

//            int tempYear = Integer.parseInt(theYear);
//            int tempDuration = Integer.parseInt(theDurationYear);
//            String totalYear = String.valueOf(tempYear + tempDuration);

            // put the information to the screen
            titleMovie.setText(theTitle);
            yearMovie.setText(theYear);
            countryMovie.setText(theCountry);
            genreMovie.setText(theGenre);
            costMovie.setText(theCost);
            keywordsMovie.setText(theKeywords);
        }
    }








////    Week 2 Extra Task
//    public void toastTwo(View view){
//        String title = movieTitle.getText().toString();
//        String genre = movieGenre.getText().toString();
//        String comments = movieComments.getText().toString();
//        Toast.makeText(getApplicationContext(), "Movie information - " + title + " , " + genre + " , " + comments, Toast.LENGTH_LONG).show();
//    }

    // Week 5 Extra task
//        double cost
//    private void doubleCost(){
//        this.cost= Integer.parseInt(movieCost.getText().toString());
//        int temp = this.cost*2;
//        movieCost.setText(String.valueOf(temp));
//    }

}