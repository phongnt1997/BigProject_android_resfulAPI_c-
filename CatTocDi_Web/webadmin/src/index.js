import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import routes from "./routes";

const myRoute = routes;

ReactDOM.render(myRoute, document.getElementById('root'));
registerServiceWorker();
