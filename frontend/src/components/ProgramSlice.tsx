import * as React from 'react';
import Box from '@mui/material/Box';
import CodeEditor from './CodeEditor';
import LineChart from './LineChart';

interface Props {
  text: string;
  marginBottom?: string;
}

export default function ProgramSlice(props: Props) {
  console.log(props.text + " hello");
  return (
    <Box sx={{ width: "100%", height: "300px", display: "flex", border: '1px solid black', marginBottom: props.marginBottom }}>
      <CodeEditor text={props.text} readOnly={true} />
      <LineChart />
    </Box>
  );
}
