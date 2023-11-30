import axios from "axios";

const handleClick = async () => {
  try {
    const response = await axios.get("http://localhost:8080/lobby/create");
    console.log(response.data);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

export default handleClick;
