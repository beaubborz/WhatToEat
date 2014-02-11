package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Quantity;
import com.beaubbe.whattoeat.models.Step;

/**
 * Created by Gab on 08/02/14.
 */
public class StepInput extends Fragment {
    private Step step;
    Context context;

    View root;

    public StepInput()
    {
    }

    public StepInput(Context c, int step_number)
    {
        step = new Step(c);
        step.setStep(step_number);
        this.context=c;
    }

    public StepInput(Step step)
    {
        this.step = step;
    }

    public Step getStep() {
        final String durationString = ((EditText)root.findViewById(R.id.duration)).getText().toString();
        final String descriptionString = ((EditText)root.findViewById(R.id.description)).getText().toString();

        //empty input.
        if(durationString.length()==0&&descriptionString.length()==0)
            return null;

        double duration = Double.parseDouble(durationString.length()>0?durationString:"0");
        int unit = ((Spinner)root.findViewById(R.id.duration_type)).getSelectedItemPosition();
        switch(unit)
        {
            case 0:
                duration*=3600;
                break;
            case 1:
                duration*=60;
                break;
        }
        step.setDuration((int)Math.round(duration));

        step.setDescription(descriptionString);
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.step_input_widget, container, false);
        this.root= root;
        TextView stepLabel = (TextView) root.findViewById(R.id.step_number);
        TextView duration = (TextView) root.findViewById(R.id.duration);
        TextView description = (TextView) root.findViewById(R.id.description);
        stepLabel.setText(String.valueOf(step.getStep()));
        duration.setText(String.valueOf(step.getDuration()));
        description.setText(String.valueOf(step.getDescription()));

        return root;
    }
}
