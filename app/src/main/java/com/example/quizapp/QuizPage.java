package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class QuizPage extends AppCompatActivity {
    TextView textView_time, textView_question_num, textView_QuestionText, textView_score;
    AppCompatRadioButton Rbutton_optionA,Rbutton_optionB,Rbutton_optionC,Rbutton_optionD;
    AppCompatButton button_next;
    RadioGroup myRadioGroup;
    JSONObject[] jsonObject = new JSONObject[0];
    HashMap<Integer,Integer> q_num_map = new HashMap<>();
    String[] correct_answer = new String[0], recorded_answer = new String[0];
    Quiz[] questionSet,curr_questionSet;
    int score =0,count=0, num_of_questions =0,time_in_seconds=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getSupportActionBar()).hide();
        setContentView(R.layout.activity_quiz_page);

        myRadioGroup = findViewById(R.id.myRadioGroup);

        Rbutton_optionA = findViewById(R.id.button_optionA);
        Rbutton_optionB = findViewById(R.id.button_optionB);
        Rbutton_optionC = findViewById(R.id.button_optionC);
        Rbutton_optionD = findViewById(R.id.button_optionD);

        button_next = findViewById(R.id.button_next);

        textView_time = findViewById(R.id.textView_time);
        textView_question_num = findViewById(R.id.textView_question_num);
        textView_QuestionText = findViewById(R.id.textView_QuestionText);
        Bundle bundle = getIntent().getExtras();
        num_of_questions = bundle.getInt("num_of_questions");
        time_in_seconds = bundle.getInt("time_in_seconds");

        correct_answer = new String[num_of_questions];
        recorded_answer = new String[num_of_questions];
        curr_questionSet = new Quiz[num_of_questions];

        // JSON to String online convertor -> https://tools.techcybo.com/json-to-string
        String json_str = "{\n  \"Data\": [\n\t  {\n\t\t\"Answer\": \"A URL\",\n\t\t\"Option A\": \"A URL\",\n\t\t\"Option B\": \"An access code\",\n\t\t\"Option C\": \"A directory\",\n\t\t\"Option D\": \"A server\",\n\t\t\"Question\": \"http://www.indiabix.com - is an example of what?\"\n\t  },\n\t  {\n\t\t\"Answer\": 8,\n\t\t\"Option A\": 4,\n\t\t\"Option B\": 8,\n\t\t\"Option C\": 12,\n\t\t\"Option D\": 16,\n\t\t\"Question\": \"How many bits is a byte?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Binary\",\n\t\t\"Option A\": \"Decimal\",\n\t\t\"Option B\": \"Octal\",\n\t\t\"Option C\": \"Binary\",\n\t\t\"Option D\": \"None of the above\",\n\t\t\"Question\": \"Computers calculate numbers in what mode?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Kbps\",\n\t\t\"Option A\": \"RAM\",\n\t\t\"Option B\": \"MHz\",\n\t\t\"Option C\": \"Kbps\",\n\t\t\"Option D\": \"Megabytes\",\n\t\t\"Question\": \"The speed of your net access is defined in terms of...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Google\",\n\t\t\"Option A\": \"ARPANET\",\n\t\t\"Option B\": \"Archie\",\n\t\t\"Option C\": \"Google\",\n\t\t\"Option D\": \"FTP\",\n\t\t\"Question\": \"Which of these is a search engine?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Central Processing Unit\",\n\t\t\"Option A\": \"Central Processing Unit\",\n\t\t\"Option B\": \"Computer Parts of USA\",\n\t\t\"Option C\": \"Commonwealth Press Union\",\n\t\t\"Option D\": \"Cute People United\",\n\t\t\"Question\": \"What does CPU stand for?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"professor@learnthenet.com\",\n\t\t\"Option A\": \"professor@learnthenet\",\n\t\t\"Option B\": \"professor@learnthenet.com\",\n\t\t\"Option C\": \"www.learnthenet.com\",\n\t\t\"Option D\": \"professor.at.learnthenet\",\n\t\t\"Question\": \"Which of these is a valid e-mail address?\"\n\t  },\n\t  {\n\t\t\"Answer\": 1,\n\t\t\"Option A\": 0,\n\t\t\"Option B\": 1,\n\t\t\"Option C\": 2,\n\t\t\"Option D\": 3,\n\t\t\"Question\": \"In a Digital circuit, what is 1 'AND' 1?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"A format for an image file\",\n\t\t\"Option A\": \"A Jumper Programmed Graphic\",\n\t\t\"Option B\": \"A type of hard disk\",\n\t\t\"Option C\": \"A format for an image file\",\n\t\t\"Option D\": \"A unit of measure for memory\",\n\t\t\"Question\": \"A JPG is...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Random Access Memory\",\n\t\t\"Option A\": \"Real Absolute Memory\",\n\t\t\"Option B\": \"Read A Manual\",\n\t\t\"Option C\": \"Random Access Memory\",\n\t\t\"Option D\": \"Really Annoying Machine\",\n\t\t\"Question\": \"RAM stands for...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Search Engine\",\n\t\t\"Option A\": \"Search Engine\",\n\t\t\"Option B\": \"Chat service on the web\",\n\t\t\"Option C\": \"Directory of images\",\n\t\t\"Option D\": \"Another name for a computer chip is ...\",\n\t\t\"Question\": \"Google (www.google.com) is a...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"STP\",\n\t\t\"Option A\": \"IP\",\n\t\t\"Option B\": \"STP\",\n\t\t\"Option C\": \"FTP\",\n\t\t\"Option D\": \"HTTP\",\n\t\t\"Question\": \"Which is not an internet protocol?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Micro chip\",\n\t\t\"Option A\": \"Microprocessor\",\n\t\t\"Option B\": \"Micro chip\",\n\t\t\"Option C\": \"Execute\",\n\t\t\"Option D\": \"Select\",\n\t\t\"Question\": \"Another name for a computer chip is ...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"World Wide Web\",\n\t\t\"Option A\": \"World Wide War\",\n\t\t\"Option B\": \"World Wide Wait\",\n\t\t\"Option C\": \"World Wide Wares\",\n\t\t\"Option D\": \"World Wide Web\",\n\t\t\"Question\": \"\\\"www\\\" stands for...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Turing\",\n\t\t\"Option A\": \"C#\",\n\t\t\"Option B\": \"Turing\",\n\t\t\"Option C\": \"Java\",\n\t\t\"Option D\": \"Basic\",\n\t\t\"Question\": \"Which of the following is not a programming language?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"The first page\",\n\t\t\"Option A\": \"The most colorful page\",\n\t\t\"Option B\": \"The first page\",\n\t\t\"Option C\": \"The last page\",\n\t\t\"Option D\": \"The largest page\",\n\t\t\"Question\": \"The \\\"home page\\\" of a web site is...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Read Only Memory\",\n\t\t\"Option A\": \"Real Obsolute Memory\",\n\t\t\"Option B\": \"Read on Monday\",\n\t\t\"Option C\": \"Read Only Memory\",\n\t\t\"Option D\": \"Royal Ontario Museum\",\n\t\t\"Question\": \"ROM stands for...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Author webpages\",\n\t\t\"Option A\": \"Solve equations\",\n\t\t\"Option B\": \"To translate\",\n\t\t\"Option C\": \"Author webpages\",\n\t\t\"Option D\": \"Plot complicated graphs\",\n\t\t\"Question\": \"HTML is used to...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"All of the above\",\n\t\t\"Option A\": \"Video\",\n\t\t\"Option B\": \"Pictures\",\n\t\t\"Option C\": \"Audio\",\n\t\t\"Option D\": \"All of the above\",\n\t\t\"Question\": \"What kind of data can you send by e-mail?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Mother board\",\n\t\t\"Option A\": \"Select\",\n\t\t\"Option B\": \"Mother board\",\n\t\t\"Option C\": \"Highlight\",\n\t\t\"Option D\": \"Decoder\",\n\t\t\"Question\": \"Main circuit board in a computer is...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Web Browser\",\n\t\t\"Option A\": \"News Reader\",\n\t\t\"Option B\": \"Graphing Package\",\n\t\t\"Option C\": \"Web Browser\",\n\t\t\"Option D\": \"Any person browsing the net\",\n\t\t\"Question\": \"Internet Explorer is a...\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Graphite\",\n\t\t\"Option A\": \"Phosphorous\",\n\t\t\"Option B\": \"Charcoal\",\n\t\t\"Option C\": \"Silicon\",\n\t\t\"Option D\": \"Graphite\",\n\t\t\"Question\": \"Which of the following is used in pencils?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"H2O\",\n\t\t\"Option A\": \"CaSiO3\",\n\t\t\"Option B\": \"Al2O3\",\n\t\t\"Option C\": \"H2O\",\n\t\t\"Option D\": \"NaAlO2\",\n\t\t\"Question\": \"Chemical formula for water is\"\n\t  },\n\t  {\n\t\t\"Answer\": \"nitrogen\",\n\t\t\"Option A\": \"oxygen\",\n\t\t\"Option B\": \"carbon dioxide\",\n\t\t\"Option C\": \"hydrogen\",\n\t\t\"Option D\": \"nitrogen\",\n\t\t\"Question\": \"The gas usually filled in the electric bulb is\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Sodium carbonate\",\n\t\t\"Option A\": \"Sodium bicarbonate\",\n\t\t\"Option B\": \"Sodium bicarbonate\",\n\t\t\"Option C\": \"Sodium carbonate\",\n\t\t\"Option D\": \"Calcium bicarbonate\",\n\t\t\"Question\": \"Washing soda is the common name for\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Diamond\",\n\t\t\"Option A\": \"Platinum\",\n\t\t\"Option B\": \"Diamond\",\n\t\t\"Option C\": \"Iron\",\n\t\t\"Option D\": \"Gold\",\n\t\t\"Question\": \"The hardest substance available on earth is\"\n\t  },\n\t  {\n\t\t\"Answer\": \"hydrogen\",\n\t\t\"Option A\": \"oxygen\",\n\t\t\"Option B\": \"sulphur\",\n\t\t\"Option C\": \"carbon\",\n\t\t\"Option D\": \"hydrogen\",\n\t\t\"Question\": \"The element common to all acids is\"\n\t  },\n\t  {\n\t\t\"Answer\": \"carbon dioxide\",\n\t\t\"Option A\": \"nitrous acid\",\n\t\t\"Option B\": \"carbon dioxide\",\n\t\t\"Option C\": \"sulphuric acid\",\n\t\t\"Option D\": \"carbonic acid\",\n\t\t\"Question\": \"Soda water contains\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Nitrous Oxide\",\n\t\t\"Option A\": \"Hydrogen peroxide\",\n\t\t\"Option B\": \"Sulphur dioxide\",\n\t\t\"Option C\": \"Carbon monoxide\",\n\t\t\"Option D\": \"Nitrous Oxide\",\n\t\t\"Question\": \"What is laughing gas?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"mixture\",\n\t\t\"Option A\": \"mixture\",\n\t\t\"Option B\": \"electrolyte\",\n\t\t\"Option C\": \"element\",\n\t\t\"Option D\": \"compound\",\n\t\t\"Question\": \"Air is a/an\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Sep-05\",\n\t\t\"Option A\": \"Oct-02\",\n\t\t\"Option B\": \"Nov-14\",\n\t\t\"Option C\": \"Jan-30\",\n\t\t\"Option D\": \"Sep-05\",\n\t\t\"Question\": \"'Teacher's Day' is observed on which of the date?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Jun-05\",\n\t\t\"Option A\": \"Jun-16\",\n\t\t\"Option B\": \"Aug-06\",\n\t\t\"Option C\": \"Jun-05\",\n\t\t\"Option D\": \"Apr-07\",\n\t\t\"Question\": \"The World Environment Day is celebrated on\"\n\t  },\n\t  {\n\t\t\"Answer\": \"soil\",\n\t\t\"Option A\": \"soil\",\n\t\t\"Option B\": \"light\",\n\t\t\"Option C\": \"atmosphere\",\n\t\t\"Option D\": \"chlorophyll\",\n\t\t\"Question\": \"Plants receive their nutrients mainly from\"\n\t  },\n\t  {\n\t\t\"Answer\": \"root hairs\",\n\t\t\"Option A\": \"zone of elongation\",\n\t\t\"Option B\": \"root hairs\",\n\t\t\"Option C\": \"growing point\",\n\t\t\"Option D\": \"embryonic zone\",\n\t\t\"Question\": \"Plants absorb most part of water needed by them through their\"\n\t  },\n\t  {\n\t\t\"Answer\": \"white light\",\n\t\t\"Option A\": \"darkness\",\n\t\t\"Option B\": \"red light\",\n\t\t\"Option C\": \"white light\",\n\t\t\"Option D\": \"yellow light\",\n\t\t\"Question\": \"Photosynthesis takes place faster in\"\n\t  },\n\t  {\n\t\t\"Answer\": \"proteins\",\n\t\t\"Option A\": \"vitamins\",\n\t\t\"Option B\": \"proteins\",\n\t\t\"Option C\": \"fats\",\n\t\t\"Option D\": \"carbohydrates\",\n\t\t\"Question\": \"Pulses are a good source of\"\n\t  },\n\t  {\n\t\t\"Answer\": \"haemoglobin\",\n\t\t\"Option A\": \"myoglobin\",\n\t\t\"Option B\": \"collagen\",\n\t\t\"Option C\": \"keratin\",\n\t\t\"Option D\": \"haemoglobin\",\n\t\t\"Question\": \"Oxygen in our blood is transported by a protein named\"\n\t  },\n\t  {\n\t\t\"Answer\": \"cockroach\",\n\t\t\"Option A\": \"cockroach\",\n\t\t\"Option B\": \"housefly\",\n\t\t\"Option C\": \"beetle\",\n\t\t\"Option D\": \"butterfly\",\n\t\t\"Question\": \"Nymph is the name of young one of\"\n\t  },\n\t  {\n\t\t\"Answer\": \"flower\",\n\t\t\"Option A\": \"flower\",\n\t\t\"Option B\": \"leaves\",\n\t\t\"Option C\": \"stem\",\n\t\t\"Option D\": \"roots\",\n\t\t\"Question\": \"Pollen grains in plants are produced in\"\n\t  },\n\t  {\n\t\t\"Answer\": 2.4,\n\t\t\"Option A\": 5.4,\n\t\t\"Option B\": 4.4,\n\t\t\"Option C\": 3.4,\n\t\t\"Option D\": 2.4,\n\t\t\"Question\": \"The percentage of earth surface covered by India is\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Coconut\",\n\t\t\"Option A\": \"Rice\",\n\t\t\"Option B\": \"Sugarcane\",\n\t\t\"Option C\": \"Cotton\",\n\t\t\"Option D\": \"Coconut\",\n\t\t\"Question\": \"Which of the following crops is regarded as a plantation crop?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Thermometer\",\n\t\t\"Option A\": \"Thermometer\",\n\t\t\"Option B\": \"Microscope\",\n\t\t\"Option C\": \"Pendulum clock\",\n\t\t\"Option D\": \"Barometer\",\n\t\t\"Question\": \"What Galileo invented?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"George Gamow\",\n\t\t\"Option A\": \"Roger Penrose\",\n\t\t\"Option B\": \"George Gamow\",\n\t\t\"Option C\": \"Michael Skube\",\n\t\t\"Option D\": \"Albert Einstein\",\n\t\t\"Question\": \"Who is the English physicist responsible for the 'Big Bang Theory'?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"1850s\",\n\t\t\"Option A\": \"1850s\",\n\t\t\"Option B\": \"1870s\",\n\t\t\"Option C\": \"1860s\",\n\t\t\"Option D\": \"1900s\",\n\t\t\"Question\": \"When were blue jeans invented?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Charles Babbage\",\n\t\t\"Option A\": \"Charles Babbage\",\n\t\t\"Option B\": \"John Mauchly\",\n\t\t\"Option C\": \"J. Presper Eckert\",\n\t\t\"Option D\": \"Philo Farnsworth\",\n\t\t\"Question\": \"This English inventor is known as the 'Father of Computing.'\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Wright Brothers\",\n\t\t\"Option A\": \"West Brothers\",\n\t\t\"Option B\": \"South Brothers\",\n\t\t\"Option C\": \"Lidenbergh Brothers\",\n\t\t\"Option D\": \"Wright Brothers\",\n\t\t\"Question\": \"Who invented the first controllable flying AEROPLANE (AIRPLANE)?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Reflecting telescope\",\n\t\t\"Option A\": \"Spectacles\",\n\t\t\"Option B\": \"Microscope\",\n\t\t\"Option C\": \"Chronometer\",\n\t\t\"Option D\": \"Reflecting telescope\",\n\t\t\"Question\": \"What Sir Isaac Newton invented?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Sleeping (railway) car\",\n\t\t\"Option A\": \"Air brakes\",\n\t\t\"Option B\": \"Box car (railway)\",\n\t\t\"Option C\": \"Airship\",\n\t\t\"Option D\": \"Sleeping (railway) car\",\n\t\t\"Question\": \"What George Pullman invented?\"\n\t  },\n\t  {\n\t\t\"Answer\": \"Volta\",\n\t\t\"Option A\": \"Volta\",\n\t\t\"Option B\": \"Galvani\",\n\t\t\"Option C\": \"Amper\",\n\t\t\"Option D\": \"Hertz\",\n\t\t\"Question\": \"The ELECTRIC BATTERY, who's charged with inventing this one?\"\n\t  }\n\t]\n}";
        try {
            JSONObject jsonRootObject = new JSONObject(json_str);
            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
            assert jsonArray != null;
            jsonObject = new JSONObject[jsonArray.length()];
            questionSet = new Quiz[jsonArray.length()];
            for(int iter=0;iter <jsonArray.length();iter++){
                jsonObject[iter] = jsonArray.getJSONObject(iter);
                questionSet[iter] = new Quiz(jsonObject[iter].optString("Question"),
                        jsonObject[iter].optString("Option A"),
                        jsonObject[iter].optString("Option B"),
                        jsonObject[iter].optString("Option C"),
                        jsonObject[iter].optString("Option D"),
                        jsonObject[iter].optString("Answer"));
                q_num_map.put(iter,iter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startQuiz(count,q_num_map,questionSet);

        //CountDown timer starts
        new CountDownTimer(time_in_seconds * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                textView_time.setText(" " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                textView_time.setText("done!");
                Intent intent = new Intent(getApplicationContext(),ResultsPage.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("score",score);
                bundle3.putInt("num_of_questions",num_of_questions);
                bundle3.putInt("time_in_seconds",time_in_seconds);
                intent.putExtras(bundle3);
                startActivity(intent);
            }

        }.start();
        //CountDown timer Ends

    }
    public static int getRandomElement(Integer[] arr){
        Random r = new Random();
        return r.nextInt(arr.length);
    }
    public void startQuiz(int count, HashMap q_num_map,Quiz[] questionSet){
        if(count == num_of_questions-2)
            button_next.setText("Submit");
        if(count >= num_of_questions){
            Intent intent = new Intent(this,ResultsPage.class);
            Bundle bundle3 = new Bundle();
            bundle3.putInt("score",score);
            bundle3.putInt("num_of_questions",num_of_questions);
            bundle3.putInt("time_in_seconds",time_in_seconds);
            intent.putExtras(bundle3);
            //intent.putExtra("curr_questionSet",curr_questionSet);
            //intent.putExtra("recorded_answer",recorded_answer);
            startActivity(intent);
        }
        myRadioGroup = findViewById(R.id.myRadioGroup);

        Rbutton_optionA = findViewById(R.id.button_optionA);
        Rbutton_optionB = findViewById(R.id.button_optionB);
        Rbutton_optionC = findViewById(R.id.button_optionC);
        Rbutton_optionD = findViewById(R.id.button_optionD);

        button_next = findViewById(R.id.button_next);

        textView_time = findViewById(R.id.textView_time);
        textView_question_num = findViewById(R.id.textView_question_num);
        textView_QuestionText = findViewById(R.id.textView_QuestionText);

        int current_question = getRandomElement((Integer[]) q_num_map.keySet().toArray(new Integer[0]));
        q_num_map.remove(current_question);
        curr_questionSet[count] = questionSet[current_question];
        textView_question_num.setText(Integer.toString(count+1));
        textView_QuestionText.setText(questionSet[current_question].question);
        Rbutton_optionA.setText(questionSet[current_question].optionA);
        Rbutton_optionB.setText(questionSet[current_question].optionB);
        Rbutton_optionC.setText(questionSet[current_question].optionC);
        Rbutton_optionD.setText(questionSet[current_question].optionD);
        correct_answer[count] = questionSet[current_question].answer;
        recorded_answer[count] = "Wrong Answer";

        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                switch(checkedId){
                    case R.id.button_optionA: //1st option selected
                        recorded_answer[count] = Rbutton_optionA.getText().toString();
                        break;
                    case R.id.button_optionB: //2nd option selected
                        recorded_answer[count] = Rbutton_optionB.getText().toString();
                        break;
                    case R.id.button_optionC: //3rd option selected
                        recorded_answer[count] = Rbutton_optionC.getText().toString();
                        break;
                    case R.id.button_optionD: //4th option selected
                        recorded_answer[count] = Rbutton_optionD.getText().toString();
                        break;

                    default:
                        recorded_answer[count] = "Wrong Answer";
                }
            }
        });
    }
    public void next_question(View view) {
        textView_score = findViewById(R.id.textView_score);
        if(recorded_answer[count].equals(correct_answer[count])) {
            score++;
            textView_score.setText(Integer.toString(score));
        }
        Rbutton_optionA.setChecked(false);
        Rbutton_optionB.setChecked(false);
        Rbutton_optionC.setChecked(false);
        Rbutton_optionD.setChecked(false);
        startQuiz(++count,q_num_map,questionSet);
    }


    //Back Button functions
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to end quiz.", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }
}