package ch.shibastudio.volleybooks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;
import ch.shibastudio.volleybooks.network.VolleyRequestQueue;

/**
 * Created by shibakaneki on 28.05.16.
 */
public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.ViewHolder>{
    private static Context context;
    private static List<Book> books = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title;
        public NetworkImageView thumb;
        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.title);
            this.thumb = (NetworkImageView)itemView.findViewById(R.id.thumb);

            this.thumb.setDefaultImageResId(R.drawable.no_pic);
            this.thumb.setErrorImageResId(R.drawable.no_pic);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_BOOK_ID, books.get(position).getId());
            context.startActivity(intent);
        }
    }

    public BooksListAdapter(Context context){
        if(null == context){
            throw new NullPointerException("context");
        }

        this.context = context;
    }

    /**
     * Sets the current books.
     * @param books as the current books.
     */
    public void setCurrentBooks(List<Book> books){
        this.books.clear();
        this.books.addAll(books);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book, parent, false);
        return new ViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Book book = this.books.get(position);
        if(null != book){
            holder.itemView.setTag(position);
            holder.title.setText(book.getTitle());
            Log.d("TEST", "[" +position +"] url: " +book.getThumbnail());
            holder.thumb.setImageUrl(book.getThumbnail(), VolleyRequestQueue.getInstance(this.context).getImageLoader());
        }
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }
}
