/* eslint-disable react/prop-types */
const Button = ({ text, className, type = "button", onClick }) => {
  const defaultClasses = "bg-blue-600 hover:bg-blue-700 p-1 m-1 min-w-8 h-6 text-xs rounded-md "; //Leave a blank at the end!!
  return (
    <button className={defaultClasses + className} type={type} onClick={onClick}>
      {text}
    </button>
  );
};

export default Button;
