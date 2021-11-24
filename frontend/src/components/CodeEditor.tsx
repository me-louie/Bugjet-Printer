import React from "react";
import AceEditor, { IMarker } from "react-ace";

import "ace-builds/src-noconflict/mode-java";

interface Props {
  text?: string;
  readOnly?: boolean;
  height?: string;
  markers: IMarker[];
}

function CodeEditor(props: Props) {
  const [text, setText] = React.useState(props.text || "");
  const editorRef = React.useRef<AceEditor>(null);

  const onChange = (newValue: string) => {
    setText(newValue);
  }

  console.log(props.text + " hello props");
  console.log(text + " hello2")

  React.useEffect(() => {
    if (props.markers.length > 0) {
      editorRef.current?.editor.scrollToLine(props.markers[0].startRow, true, true, () => {});
    }
  }, [props.markers]);


  return (
    <AceEditor
      ref={editorRef}
      placeholder="Write your code here"
      mode="java"
      name="hello"
      height={props.height || "100%"}
      readOnly={props.readOnly}
      value={props.readOnly ? props.text : text}
      onChange={onChange}
      fontSize={14}
      highlightActiveLine={true}
      markers={props.markers}
      setOptions={{
        // enableBasicAutocompletion: false,
        // enableLiveAutocompletion: true,
        showLineNumbers: true,
        tabSize: 2,
      }} />

  );
}

export default CodeEditor;
