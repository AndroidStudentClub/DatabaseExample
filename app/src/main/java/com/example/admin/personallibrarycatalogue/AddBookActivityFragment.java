package com.example.admin.personallibrarycatalogue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.personallibrarycatalogue.data.Book;
import com.example.admin.personallibrarycatalogue.data.LibraryDatabaseHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddBookActivityFragment extends Fragment {

    private final static String TITLE = "Title";
    private final static String AUTHOR = "Author";
    private final static String PLEASE_ENTER_THE_AUTHOR_FIELD = "Please enter the author field";
    private final static String PLEASE_ENTER_THE_TITLE_FIELD =  "Please enter the title field";
    private ImageView imageView_;
    private final int SELECT_PHOTO = 1;
    private boolean isEditMode_ = false;
    private Integer id_ ;

    public AddBookActivityFragment newInstance(String title, String author) {
        AddBookActivityFragment fragment = new AddBookActivityFragment();

        // arguments
        Bundle arguments = new Bundle();
        arguments.putString(TITLE, title);
        arguments.putString(AUTHOR, author);
        fragment.setArguments(arguments);
        return fragment;
    }

    public AddBookActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_add_book, container, false);
        final LibraryDatabaseHelper helper = new LibraryDatabaseHelper(getActivity());

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            isEditMode_ = true;
            id_ = getArguments().getInt("id");

            fillAllFields(rootView, helper, id_);
        }

        imageView_ = (ImageView) rootView.findViewById(R.id.cover_image_view);

        // Actions for change cover button
        Button setImageButton = (Button) rootView.findViewById(R.id.set_image_button);

        setImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        // Actions for ok - button (Checking fields, insert into database, start new activity)
        Button okButton = (Button) rootView.findViewById(R.id.apply_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText authorEditText = (EditText) rootView.findViewById(R.id.author_edit_text);
                String author = authorEditText.getText().toString();

                EditText titleEditText = (EditText) rootView.findViewById(R.id.title_edit_text);
                String title = titleEditText.getText().toString();

                EditText descriptionEditText = (EditText) rootView.findViewById(R.id.description_edit_text);
                String description = descriptionEditText.getText().toString();

                ImageView coverView = (ImageView) rootView.findViewById(R.id.cover_image_view);
                byte[] cover = Util.getBytesFromDrawable(coverView.getDrawable());

                if (author.matches("")) {
                    Toast.makeText(getActivity(), PLEASE_ENTER_THE_AUTHOR_FIELD, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (title.matches("")) {
                    Toast.makeText(getActivity(), PLEASE_ENTER_THE_TITLE_FIELD, Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book = new Book(id_,title, author, description, cover);

                // in case user edit information just update data
                if (id_ != null) {
                    helper.updateBook(id_, book);
                } else {
                    helper.addBook(book);
                }

                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                startActivity(intent);

            }
        });

        // Actions for cancel button (clear fields and leave activity)
        Button cancelButton = (Button) rootView.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) rootView.findViewById(R.id.add_book_layout);
                clearForm(group);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * Clears all edtiText fileds
     */
    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

    private void fillAllFields(View view, LibraryDatabaseHelper helper, int id) {

        Book book = helper.getBookById(id);

        EditText titleEditText = (EditText) view.findViewById(R.id.title_edit_text);
        titleEditText.setText(book.getTitle());

        EditText authorEditText = (EditText) view.findViewById(R.id.author_edit_text);
        authorEditText.setText(book.getAuthor());

        EditText descriptionEditText = (EditText) view.findViewById(R.id.description_edit_text);
        descriptionEditText.setText(book.getDescription());

        ImageView coverView = (ImageView) view.findViewById(R.id.cover_image_view);
        coverView.setImageBitmap(Util.getBitmapFromBytes(book.getCover()));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView_.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
