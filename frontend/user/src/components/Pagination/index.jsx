import React from 'react';
import { Link } from 'react-router-dom';

const range = (start, end) => {
    let length = end - start + 1;
    /*
        Create an array of certain length and set the elements within it from
      start value to end value.
    */
    return Array.from({ length }, (_, idx) => idx + start);
};

const Pagination = props => {
    const {
        totalPage,
        currentPage,
        handleNextPage
    } = props;

    const leftSiblingIndex = Math.max(currentPage - 1, 1);
    const rightSiblingIndex = Math.min(
        currentPage + 1,
        totalPage
    );

    const pageRange = range(leftSiblingIndex, rightSiblingIndex);
    
    return (
        <ul className="pagination justify-content-center mb-3">
            <li onClick={() => handleNextPage(currentPage - 1)} className={currentPage == 1 ? "page-item disabled" : "page-item"}>
                <span to="#" className="page-link" aria-label="Previous">
                    <span aria-hidden="true">«</span>
                    <span className="sr-only">Previous</span>
                </span>
            </li>

            {
                pageRange.map((pageNumber, index) => {
                    return (
                        <li className={pageNumber == currentPage ? "page-item active" : "page-item"}>
                            <span to="#" onClick={() => handleNextPage(pageNumber)} className="page-link"> {pageNumber} </span>
                        </li>
                    );
                })}

            <li className={currentPage < totalPage ? "page-item" : "page-item disabled"} >
                <span to="#" onClick={() => handleNextPage(currentPage + 1)} className="page-link" aria-label="Next"> <span aria-hidden="true">»</span>
                    <span className="sr-only">Next</span>
                </span>
            </li>
        </ul>
    )
};

export default Pagination;