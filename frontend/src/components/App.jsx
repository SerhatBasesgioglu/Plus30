import LobbyForm from "./LobbyForm";
import Header from "./Header";
import LobbyList from "./LobbyList";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    <div className="App container bg-success bg-opacity-75">
      <Header />
      <div className="row">
        <LobbyForm className="col-4" />
        <div className="col-2" />
        <LobbyList className="col-6" />
      </div>
    </div>
  );
}

export default App;
