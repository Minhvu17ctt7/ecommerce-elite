import React, { useEffect, useState } from 'react'
import { Toast } from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux';
import { closeToast } from '../../redux/actions/toastActions';

const ToastComponent = () => {
    const dispatch = useDispatch();
    const { show, data } = useSelector(state => state.toast);
    const handleCloseToast = () => {
        dispatch(closeToast());
    }

    useEffect(() => {
        const timer = setTimeout(() => {
            dispatch(closeToast());
        }, 5000);
        return () => clearTimeout(timer);
    })
    return (
        <>
            <Toast className="bg-danger" style={{
                position: "fixed", zIndex: "100", top: 20,
                right: 30
            }} onClose={handleCloseToast} show={show} delay={2000} animation autohide>
                <Toast.Header>
                    <strong className="mr-auto">{data.title}</strong>
                </Toast.Header>
                <Toast.Body className='text-dark'>{data.message}</Toast.Body>
            </Toast>
        </>
    )
}

export default ToastComponent