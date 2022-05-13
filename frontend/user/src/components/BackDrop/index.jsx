import React from 'react'
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';

const BackDrop = () => {
    return (
        <Backdrop
            sx={{ color: '#fff', backgroundColor: 'white', zIndex: (theme) => theme.zIndex.drawer + 1 }}
            open={true}
        >
            <CircularProgress />
        </Backdrop>
    )
}

export default BackDrop