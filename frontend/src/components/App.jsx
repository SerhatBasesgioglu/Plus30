import LobbyForm from "./LobbyForm";
import Header from "./Header";
import LobbyList from "./LobbyList";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    <div className="App container">
      <Header />
      <LobbyForm />
      <LobbyList />
    </div>
  );
}

export default App;
