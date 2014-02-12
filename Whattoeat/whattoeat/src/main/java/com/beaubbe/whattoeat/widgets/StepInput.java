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

        this.root = inflater.inflate(R.layout.step_input_widget, container, false);

        if(step.getId()>0)
        {
            TextView stepLabel = (TextView) root.findViewById(R.id.step_number);
            TextView description = (TextView) root.findViewById(R.id.description);
            TextView duration = (TextView) root.findViewById(R.id.duration);
            Spinner durationType = (Spinner) root.findViewById(R.id.duration_type);
            stepLabel.setText(String.valueOf(step.getStep()));
            description.setText(String.valueOf(step.getDescription()));

            //duration:
            double d = step.getDuration();
            int dType = 2; // [2] = seconds
            if(d>=3600)
            {
                d /= 3600.0;
                dType = 0;
            }
            else if(d>=60)
            {
                d /= 60;
                dType = 1;
            }
            duration.setText(String.valueOf(d));
            durationType.setSelection(dType);
        }

        return root;
    }
}
