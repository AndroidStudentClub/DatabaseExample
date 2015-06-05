package com.example.admin.personallibrarycatalogue;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.personallibrarycatalogue.data.Book;
import com.example.admin.personallibrarycatalogue.data.LibraryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A placeholder fragment containing a simple view.
 */
public class BooksListActivityFragment extends Fragment {

    private final static String ID = "id";
    private ListView listView_;
    private BooksListAdapter booksListAdapter_;
    private LibraryDatabaseHelper helper_;

    public BooksListActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_books_list, container, false);

        helper_ = new LibraryDatabaseHelper(getActivity());

        List<Book> booksList = new ArrayList<Book>();
        booksList = helper_.getAllBooks();

        listView_ = (ListView) rootView.findViewById(R.id.books_list_view);
        booksListAdapter_ = new BooksListAdapter(this.getActivity(), booksList);
        listView_.setAdapter(booksListAdapter_);

        // Set ContextMenu for listView
        registerForContextMenu(listView_);

        listView_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeBook(parent, position);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderTitle(R.string.Menu);
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.edit_book:
                changeBook(listView_, info.position);
                break;

            case R.id.delete_book:
                deleteBook(listView_, info.position);
                booksListAdapter_.notifyDataSetChanged();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    /**
     * Set up activity for changing some information about book (title, author etc.)
     *
     * @param parent   AdapterView (could be a ListView, GridView, Spinner)
     * @param position Clicked item position
     */
    public void changeBook(AdapterView<?> parent, int position) {
        Book book = (Book) parent.getItemAtPosition(position);
        String author = book.getAuthor();
        String title = book.getTitle();

        Intent intent = new Intent();
        intent.setClass(getActivity(), AddBookActivity.class);

        int returned_id = helper_.getId(title, author);

        intent.putExtra(ID, returned_id);
        startActivity(intent);
    }

    public void deleteBook(AdapterView<?> parent, int position) {
        Book book = (Book) parent.getItemAtPosition(position);
        String author = book.getAuthor();
        String title = book.getTitle();

        int returnedId = helper_.getId(title, author);
        // Delete from database
        helper_.deleteBook(returnedId);
        // Delete from adapter
        booksListAdapter_.remove(book);
    }
}
