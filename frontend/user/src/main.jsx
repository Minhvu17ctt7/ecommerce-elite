import { SnackbarProvider } from 'notistack'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux'
import App from './App'
import './index.css'
import configureStore from './redux/store/configureStore'

const store = configureStore();

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <SnackbarProvider maxSnack={1} anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}>
        <App />
      </SnackbarProvider>
    </Provider>
  </React.StrictMode>
)
