package com.hkr.quizme.ui.createQuiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hkr.quizme.MainActivity;
import com.hkr.quizme.R;
import com.hkr.quizme.database_utils.entities.Answer;
import com.hkr.quizme.database_utils.entities.Course;
import com.hkr.quizme.database_utils.entities.Quiz;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Dialog extends DialogFragment implements View.OnClickListener {

    public RecyclerView recyclerView;
    private CreateQuizAdapter createQuizAdapter;
    private Button createQuizAfterReviewBtn;
    private int i = 0;
    private int index = -1;
    private int indexTwo = -1;
    private Button courseBtn, subjectBtn, submitBtn, caneclBtn;
    private boolean checkCoursePicked = false;
    private List<Course> courses;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.review_quiz_alert_dialog_layout, container, false);
        recyclerView = view.findViewById(R.id.review_recycler_view);
        courses = Course.getCourses();
        createQuizAfterReviewBtn = view.findViewById(R.id.create_quiz_after_review);
        createQuizAfterReviewBtn.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        createQuizAdapter = new CreateQuizAdapter(this.getContext(), Question.getQuestions());
        recyclerView.setAdapter(createQuizAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (i > 0) {
            createQuizAdapter = new CreateQuizAdapter(this.getContext(), Question.getQuestions());
            recyclerView.setAdapter(createQuizAdapter);
        }
        i = 1;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCancelable(true);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = 900;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);

    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_anim);
        if (v == createQuizAfterReviewBtn) {
            v.startAnimation(animation);
            if (Question.getQuestions().size() == 0) {
                Toast.makeText(getContext(), "Unable to create quiz since you did not add any question", Toast.LENGTH_LONG).show();
                getDialog().dismiss();

            } else {
                getQuizInfo();
            }
        }
    }

    private void getQuizInfo() {
        final android.app.Dialog dialog = new android.app.Dialog(getContext());
        dialog.setContentView(R.layout.create_quiz_parent_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        courseBtn = dialog.findViewById(R.id.course_btn);
        subjectBtn = dialog.findViewById(R.id.subject_btn);
        submitBtn = dialog.findViewById(R.id.submitBtn);
        caneclBtn = dialog.findViewById(R.id.cancelBtn);
        final EditText quizName = dialog.findViewById(R.id.quiz_name);


        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCourseDialog();
            }
        });

        subjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkCoursePicked) {
                    Toast.makeText(getContext(), "You have to choose a course first", Toast.LENGTH_LONG).show();
                } else {
                    pickSubjectDialog();
                }

            }
        });

        caneclBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseBtn.getText().equals("Belongs to course?") || subjectBtn.getText().equals("Belongs to subject?") || quizName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "You have to indicate all the requirements fields to be able to submit you quiz", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Quiz quiz = new Quiz(quizName.getText().toString());
                    quiz.setSubjectId(courses.get(index).getSubjects().get(indexTwo).getId());
                    for (Question q : Question.getQuestions()) {
                        com.hkr.quizme.database_utils.entities.Question dbQuestion = new com.hkr.quizme.database_utils.entities.Question(q.getQuestion());
                        if (q.getCorrectAnswerOne().equals("")) {
                            Toast.makeText(getContext(), "Must provide at least one correct answer.", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            dbQuestion.getAnswers().add(new Answer(true, q.getCorrectAnswerOne()));
                        }
                        if (q.getWrongAnswerOne().equals("")) {
                            Toast.makeText(getContext(), "Must provide at least one wrong answer.", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            dbQuestion.getAnswers().add(new Answer(false, q.getWrongAnswerOne()));
                        }

                        if (!q.getWrongAnswerTwo().equals("")) {
                            dbQuestion.getAnswers().add(new Answer(false, q.getWrongAnswerTwo()));
                        }
                        if (!q.getWrongAnswerThree().equals("")) {
                            dbQuestion.getAnswers().add(new Answer(false, q.getWrongAnswerThree()));
                        }
                        if (!q.getWrongAnswerFour().equals("")) {
                            dbQuestion.getAnswers().add(new Answer(false, q.getWrongAnswerFour()));
                        }
                        if (!q.getCorrectAnswerTwo().equals("")) {
                            dbQuestion.getAnswers().add(new Answer(true, q.getCorrectAnswerTwo()));
                        }
                        quiz.getQuestions().add(dbQuestion);
                        Log.d("Dialog::", quiz.getQuestions().get(0).getQuestion());
                    }
                    quiz.insert();
                    Toast.makeText(getContext(), "Your quiz has been created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    Question.getQuestions().clear();
                    startActivity(intent);
                }
            }
        });

        dialog.show();
    }


    public void pickCourseDialog() {
        final String[] courseStrings = new String[courses.size()];
        for (int i = 0; i < courseStrings.length; i++) {
            courseStrings[i] = courses.get(i).getName();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setTitle("Pick a Course");
        builder.setSingleChoiceItems(courseStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (index != -1) {
                    courseBtn.setText(courseStrings[index]);
                    checkCoursePicked = true;
                }
                if (indexTwo != -1) {
                    subjectBtn.setText("Belongs to subject?");
                }
            }
        });
        builder.show();
    }

    public void pickSubjectDialog() {
        final String[] subjects = new String[courses.get(index).getSubjects().size()];
        for (int i = 0; i < subjects.length; i++) {
            subjects[i] = courses.get(index).getSubjects().get(i).getName();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setTitle("Pick a Subject");

        builder.setSingleChoiceItems(subjects, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                indexTwo = which;
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (indexTwo != -1) {
                    subjectBtn.setText(subjects[indexTwo]);
                }
            }
        });
        builder.show();
    }


}
