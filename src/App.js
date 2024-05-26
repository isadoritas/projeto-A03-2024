import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import './App.css';
import Navbar from "./layout/Navbar";
import AddMovie from "./movies/AddMovie";
import Home from "./pages/Home";

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar/>
        
        <Routes>
          <Route exact path="/" element={<Home/>}/>
          <Route exact path="/addmovie" element={<AddMovie/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
