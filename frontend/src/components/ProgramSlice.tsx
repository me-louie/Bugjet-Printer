import * as React from 'react';
import Box from '@mui/material/Box';
// import CodeEditor from './CodeEditor';
import LineChart from './LineChart';
import LabelButton from './LabelButton';
import { Output } from '../mocks/output';
import { IMarker } from 'react-ace';
import BarChart from './BarChart';
import BarChartSlider from './BarChartSlider';
import TwoDarraySlider from './2DarraySlider';

interface Props {
  name: string;
  // text: string;
  marginBottom?: string;
  output: Output;
  setMarker: (arr: IMarker[]) => void;
}

export default function ProgramSlice(props: Props) {

  const getChart = () => {
    switch (props.output.type) {
      case "int[][]":
        return <TwoDarraySlider output={props.output} setMarker={props.setMarker} />;
      case "int[]":
        return <BarChartSlider output={props.output} setMarker={props.setMarker} />;
      case "int":
      case "double":
      case "float":
        return <LineChart output={props.output} setMarker={props.setMarker} />
      default: 
        return null;   
    }
  }

  return (
    <Box sx={{ width: "100%", height: "300px", display: "flex", 
               border: '1px solid black', marginBottom: props.marginBottom, borderRadius: "5px" }}>
      <LabelButton name = {props.name}/>
      {/* <CodeEditor text={props.text} readOnly={true} /> */}
      {getChart()}
      {/* //<LineChart output={props.output} setMarker={props.setMarker} /> */}
    </Box>
  );
}
