function App() {
  const color = ["Red", "Orange", "Blue", "Green"];
  const colorChanged = color.map((x) => <p>{x}</p>);
  return <div className="App">{colorChanged}</div>;
}

export default App;
