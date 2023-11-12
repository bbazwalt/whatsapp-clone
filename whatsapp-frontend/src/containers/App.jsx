import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home";
import Signup from "../pages/Signup";
import Signin from "../pages/Signin";
const App = () => {
  return (
    <div>
      <Routes>
        <Route exact path="/" element={<Home />}></Route>
        <Route path="/signin" element={<Signin />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
      </Routes>
    </div>
  );
};

export default App;
