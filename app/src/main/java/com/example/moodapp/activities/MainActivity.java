package com.example.moodapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.moodapp.R;
import com.example.moodapp.adapters.MoodAdapter;
import com.example.moodapp.database.Database;
import com.example.moodapp.entities.Mood;
import com.example.moodapp.listeners.MoodsListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoodsListener {

    public static final int REQUEST_CODE_ADD_MOOD = 1;
    public static final int REQUEST_CODE_UPDATE_MOOD = 2;

    public static final int REQUEST_CODE_SHOW_MOOD = 3;

    public static final int REQUEST_CODE_STORAGE_PERMISSION = 5;

    private RecyclerView notesRecyclerView;

    private List<Mood> moodList;
    private MoodAdapter moodAdapter;

    private int noteClickedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), CreateMoodActivity.class), REQUEST_CODE_ADD_MOOD)
        );

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        moodList = new ArrayList<>();
        moodAdapter = new MoodAdapter(moodList, this);
        notesRecyclerView.setAdapter(moodAdapter);

        getNotes(REQUEST_CODE_SHOW_MOOD, false);

        EditText inputSearch = findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                moodAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (moodList.size() != 0) {
                    moodAdapter.searchMoods(s.toString());
                }
            }
        });

        findViewById(R.id.imageAddNote).setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), CreateMoodActivity.class), REQUEST_CODE_ADD_MOOD));
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

    @Override
    public void onClickedNote(Mood mood, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("mood", mood);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_MOOD);
    }

    private void getNotes(final int requestCode, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetMoodTask extends AsyncTask<Void, Void, List<Mood>> {

            @Override
            protected List<Mood> doInBackground(Void... voids) {
                return Database.getDatabase(getApplicationContext())
                        .MoodDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Mood> moods) {
                super.onPostExecute(moods);
                if (requestCode == REQUEST_CODE_SHOW_MOOD) {
                    moodList.addAll(moods);
                    moodAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_MOOD) {
                    moodList.add(0, moods.get(0));
                    moodAdapter.notifyItemInserted(0);
                    notesRecyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_MOOD) {
                    moodList.remove(noteClickedPosition);
                    if (isNoteDeleted) {
                        moodAdapter.notifyItemRemoved(noteClickedPosition);
                    } else {
                        moodList.add(noteClickedPosition, moods.get(noteClickedPosition));
                        moodAdapter.notifyItemChanged(noteClickedPosition);
                    }
                }
            }
        }

        new GetMoodTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_MOOD && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_ADD_MOOD, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_MOOD && resultCode == RESULT_OK) {
            if (data != null) {
                getNotes(REQUEST_CODE_UPDATE_MOOD, data.getBooleanExtra("isNoteDeleted", false));
            }
        }
    }

}