import * as React from 'react';
import Box from '@mui/material/Box';
// import CodeEditor from './CodeEditor';
import LineChart from './LineChart';
import LabelButton from './LabelButton';
import { Output } from '../mocks/output';

interface Props {
  name: string;
  // text: string;
  marginBottom?: string;
  output: Output;
}

export default function ProgramSlice(props: Props) {
  return (
    <Box sx={{ width: "100%", height: "300px", display: "flex",
               border: '1px solid black', marginBottom: props.marginBottom, borderRadius: "5px" }}>
      <LabelButton name = {props.name}/>
      {/* <CodeEditor text={props.text} readOnly={true} /> */}
      <LineChart output={props.output} />
    </Box>
  );
}
