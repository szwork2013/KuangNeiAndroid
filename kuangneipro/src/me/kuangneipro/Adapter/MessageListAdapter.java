package me.kuangneipro.Adapter;

import java.util.List;

import me.kuangneipro.R;
import me.kuangneipro.entity.MessageInfo;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageListAdapter extends ArrayAdapter<MessageInfo>{
	public String tag = this.getClass().getSimpleName(); // tag 用于测试log用
	private final Activity context;
	private final List<MessageInfo> messages;

	static class ViewHolder {
		private TextView content;
		private TextView from;
		private TextView to;
		private TextView reply;
		private TextView date;
		private View split;
	}

	public MessageListAdapter(Activity context,
			List<MessageInfo> messages) {
		super(context, R.layout.item_reply, messages);
		this.context = context;
		this.messages = messages;
	}

	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public MessageInfo getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(tag, "getView" + position);
		View rowView = convertView;
		ViewHolder viewHolder = null;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_reply, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.content = (TextView) rowView.findViewById(R.id.content);
			viewHolder.from = (TextView) rowView.findViewById(R.id.from);
			viewHolder.to = (TextView) rowView.findViewById(R.id.to);
			viewHolder.date = (TextView) rowView.findViewById(R.id.date);
			viewHolder.reply = (TextView) rowView.findViewById(R.id.reply);
			viewHolder.split = rowView.findViewById(R.id.split);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		MessageInfo message = messages.get(position);
		holder.date.setText(message.replyInfo.getDate());

		holder.to.setVisibility(View.VISIBLE);
		holder.reply.setVisibility(View.VISIBLE);
		holder.to.setText(message.replyInfo.toUser.name);

		holder.from.setText(message.replyInfo.fromUser.name);
		holder.content.setText(message.replyInfo.content);
		if (position == getCount() - 1)
			holder.split.setVisibility(View.GONE);
		else
			holder.split.setVisibility(View.VISIBLE);

		return rowView;
	}

}