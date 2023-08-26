import {Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";

const App = () => {
  return (
    <div >
      <Routes>
        <Route path="/" element={<HomePage/>}></Route>
        </Routes>
    </div>
  );
}

export default App;
