import React from "react";
import AceEditor from "react-ace";

import "ace-builds/src-noconflict/mode-java";

interface Props {
  text?: string;
  readOnly?: boolean;
  height?: string;
}

function CodeEditor(props: Props) {
  const [text, setText] = React.useState(props.text || "");

  const onChange = (newValue: string) => {
    setText(newValue);
  }

  console.log(props.text + " hello props");
  console.log(text + " hello2")

  return (
    <AceEditor
      placeholder="Write your code here"
      mode="java"
      name="hello"
      height={props.height || "100%"}
      readOnly={props.readOnly}
      value={props.readOnly ? props.text : text}
      onChange={onChange}
      fontSize={14}
      highlightActiveLine={true}
      setOptions={{
        // enableBasicAutocompletion: false,
        // enableLiveAutocompletion: true,
        showLineNumbers: true,
        tabSize: 2,
      }} />

  );
}

export default CodeEditor;
