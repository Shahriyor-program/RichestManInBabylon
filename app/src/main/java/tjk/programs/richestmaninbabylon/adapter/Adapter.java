package tjk.programs.richestmaninbabylon.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tjk.programs.richestmaninbabylon.ClickListener;
import tjk.programs.richestmaninbabylon.R;
import tjk.programs.richestmaninbabylon.model.Root;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Root> data;
    ClickListener clickListener;

    public Adapter(Context context, List<Root> data, ClickListener clickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = data.get(position).getTitle();
        String description = data.get(position).getDescription();
        holder.title.setText(title);
        holder.description.setText(Html.fromHtml(description));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(data.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        ImageView btnPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            Intent i = new Intent(itemView.getContext(), DetailAudioActivity.class);
////            i.putExtra("title", data.get(getAdapterPosition()));
//            itemView.getContext().startActivity(i);

            title = itemView.findViewById(R.id.nameChapter);
            description = itemView.findViewById(R.id.descChapter);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }
    }
}
