# Multistorey-Parking-Lot-Simulation
A parking lot simulation created using AI algorithms to store and retrieve cars

This project implements search algorithms to 
manage multiple concurrent requests, in real time and in a dynamic context, for storage 
and retrieval of robotic load-carrying carts for automated parking 
lots or storage warehouses. A set of informed search algorithms including D* Lite and 
A* with domain-specific heuristics, and the uninformed search algorithm Uniform Cost 
Search are integrated for path search and planning in a completely-automated framework. 
The problem domain is considered as a rectangular array of parking or storage cells with 
several cells allocated to entry-exit points such as elevators: the storage topology does not 
have any driving lanes other than an allocation of blank cells where all storage is 
conceived to be on moveable carts. It is further assumed that the entire floor can be fully 
occupied with the exception of blank cells, which need to be leveraged to form temporary 
passageways for carts on the move for storage or retrieval. The number of blank cells is 
determined to maximize the storage or parking capacity and yet must be large enough to 
facilitate to serve the multiple and concurrent storage and retrieval requests in real time. 
Multiple carts are considered to be potentially moving in a layout where each moving cart 

