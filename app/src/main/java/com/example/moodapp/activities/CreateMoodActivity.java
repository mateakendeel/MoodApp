package com.example.moodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodapp.R;
import com.example.moodapp.database.Database;
import com.example.moodapp.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateMoodActivity extends AppCompatActivity {
    private static final String TAG = CreateMoodActivity.class.getSimpleName();

    private EditText inputTitle, inputSubtitle, inputText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;

    private String selectedMoodColor;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private AlertDialog dialogDeleteMood;

    private Note availableNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mood);

        ImageView imageBack = findViewById(R.id.imageArrowBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        inputTitle = findViewById(R.id.inputTittleName);
        inputSubtitle = findViewById(R.id.inputSubtitle);
        inputText = findViewById(R.id.inputNoteText);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);


        textDateTime = findViewById(R.id.textDateTime);
        textDateTime.setText(new SimpleDateFormat(
                "EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date().getTime())
        );

        ImageView imageSave = findViewById(R.id.imageSaveNote);
        imageSave.setOnClickListener(v -> saveMood());

        selectedMoodColor = "#343841";

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            availableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        initMixed();
        setSubtitleIndicatorColor();
    }

    private void setViewOrUpdateNote() {
        inputTitle.setText(availableNote.getTitle());
        inputSubtitle.setText(availableNote.getSubtitle());
        inputText.setText(availableNote.getNoteText());
        textDateTime.setText(availableNote.getDateTime());

    }

    private void saveMood() {
        final String noteTitle = inputTitle.getText().toString().trim();
        final String noteSubtitle = inputSubtitle.getText().toString().trim();
        final String noteText = inputText.getText().toString().trim();
        final String dateTimeStr = textDateTime.getText().toString().trim();

        if (noteTitle.isEmpty()) {
            Toast.makeText(this, "Note title can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (noteSubtitle.isEmpty() && noteText.isEmpty()) {
            Toast.makeText(this, "Note can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(noteTitle);
        note.setSubtitle(noteSubtitle);
        note.setNoteText(noteText);
        note.setDateTime(dateTimeStr);
        note.setColor(selectedMoodColor);

        if (availableNote != null) {
            note.setId(availableNote.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class SaveMoodTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Database.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        }

        new SaveMoodTask().execute();
    }

    private void initMixed() {
        final LinearLayout layoutChangeColor = findViewById(R.id.layoutChangeColor);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutChangeColor);
        layoutChangeColor.findViewById(R.id.textChangeColor).setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        final ImageView imageColor1 = layoutChangeColor.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layoutChangeColor.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layoutChangeColor.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layoutChangeColor.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = layoutChangeColor.findViewById(R.id.imageColor5);

        layoutChangeColor.findViewById(R.id.viewColor1).setOnClickListener(v -> {
            selectedMoodColor = "#FFC107";
            imageColor1.setImageResource(R.drawable.ic_save);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutChangeColor.findViewById(R.id.viewColor2).setOnClickListener(v -> {
            selectedMoodColor = "#2196F3";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_save);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutChangeColor.findViewById(R.id.viewColor3).setOnClickListener(v -> {
            selectedMoodColor = "#F62D1E";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_save);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutChangeColor.findViewById(R.id.viewColor4).setOnClickListener(v -> {
            selectedMoodColor = "#673AB7";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_save);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutChangeColor.findViewById(R.id.viewColor5).setOnClickListener(v -> {
            selectedMoodColor = "#E91E63";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_save);
            setSubtitleIndicatorColor();
        });

        if (availableNote != null) {
            final String noteColorCode = availableNote.getColor();
            if (noteColorCode != null && !noteColorCode.trim().isEmpty()) {
                switch (noteColorCode) {
                    case "#2196F3":
                        layoutChangeColor.findViewById(R.id.viewColor2).performClick();
                        break;
                    case "#F62D1E":
                        layoutChangeColor.findViewById(R.id.viewColor3).performClick();
                        break;
                    case "#673AB7":
                        layoutChangeColor.findViewById(R.id.viewColor4).performClick();
                        break;
                    case "#E91E63":
                        layoutChangeColor.findViewById(R.id.viewColor5).performClick();
                        break;
                }
            }
        }


        if (availableNote != null) {
            layoutChangeColor.findViewById(R.id.layoutDeleteMood).setVisibility(View.VISIBLE);
            layoutChangeColor.findViewById(R.id.layoutDeleteMood).setOnClickListener(v -> {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                showDeleteMoodDialog();
            });
        }
    }

    private void showDeleteMoodDialog() {
        if (dialogDeleteMood == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateMoodActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_delete_mood,
                    (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer)
            );
            builder.setView(view);
            dialogDeleteMood = builder.create();
            if (dialogDeleteMood.getWindow() != null) {
                dialogDeleteMood.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteMood).setOnClickListener(v -> {
                @SuppressLint("StaticFieldLeak")
                class DeleteMoodTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        Database.getDatabase(getApplicationContext()).noteDao().deleteNote(availableNote);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Intent intent = new Intent();
                        intent.putExtra("isNoteDeleted", true);
                        setResult(RESULT_OK, intent);

                        dialogDeleteMood.dismiss();
                        finish();
                    }
                }

                new DeleteMoodTask().execute();
            });

            view.findViewById(R.id.textCancel).setOnClickListener(v -> dialogDeleteMood.dismiss());
        }

        dialogDeleteMood.show();
    }

    private void setSubtitleIndicatorColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedMoodColor));
    }



    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

}