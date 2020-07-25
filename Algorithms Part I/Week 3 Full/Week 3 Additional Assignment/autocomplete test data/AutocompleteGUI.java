/* *****************************************************************************
 *  Compilation:  javac-algs4 AutocompleteGUI.java
 *  Execution:    java-algs4  AutocompleteGUI input.txt k
 *  Dependencies: Autocomplete.java Term.java
 *
 *  @author Matthew Drabick
 *  @author Ming-Yee Tsang
 *  @author Andrew Ward
 *  @author Kevin Wayne
 *
 *  Interactive GUI used to demonstrate the Autocomplete data type.
 *
 *     * Reads a list of terms and weights from a file, specified as a
 *       command-line argument.
 *
 *     * As the user types in a text box, display the top-k terms
 *       that start with the text that the user types.
 *
 *     * Displays the result in a browser if the user selects a term
 *       (by pressing enter, clicking a selection, or pressing the
 *       "Search Google" button).
 *
 *
 *  BUG: Search bar and suggestion drop-down don't resize properly with window;
 *       they stay the same size when the window gets wider, and the weights
 *       get hidden when the window gets smaller.
 *
 *  FEATURE: make weights be in left column instead of right column ?
 *           (to match toString() and output format of test client on assignment)
 *
 *  NOTE: Requires Java 7 (or above) because JList was not retrofitted to support
 *        generics until Java 7.
 *
 *
 *  % java-algs4 AutocompleteGUI cities.txt 10
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


public class AutocompleteGUI extends JFrame {
    // for serializable classes
    private static final long serialVersionUID = 1L;

    private static final int DEF_WIDTH = 850; // width of the GUI window
    private static final int DEF_HEIGHT = 400; // height of the GUI window

    // URL prefix for searches
    private static final String SEARCH_URL = "https://www.google.com/search?q=";

    // Display top k results
    private final int k;

    // Indicates whether to display weights next to query matches
    private boolean displayWeights = true;

    /**
     * Initializes the GUI, and the associated Autocomplete object
     *
     * @param filename the file to read all the autocomplete data from
     * @param k        the maximum number of suggestions to return
     */
    public AutocompleteGUI(String filename, int k) {
        this.k = k;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Autocomplete Me");
        setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        pack();
        setLocationRelativeTo(null);
        Container content = getContentPane();
        GroupLayout layout = new GroupLayout(content);
        content.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        final AutocompletePanel ap = new AutocompletePanel(filename);

        JLabel textLabel = new JLabel("Search query:");
        textLabel.setLabelFor(ap);

        // Create and add a listener to the Search button
        JButton searchButton = new JButton("Search Google");
        searchButton.addActionListener(
                ae -> searchOnline(ap.getSelectedText()));

        // Create and add a listener to a "Show weights" checkbox
        JCheckBox checkbox = new JCheckBox("Show weights", null, displayWeights);
        checkbox.addActionListener(
                ae -> {
                    displayWeights = !displayWeights;
                    ap.update();
                });

        // Define the layout of the window
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                      .addGroup(layout.createParallelGroup(
                              GroupLayout.Alignment.TRAILING)
                                      .addComponent(textLabel, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE)
                                      .addComponent(checkbox, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                       GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                      .addComponent(ap, 0, GroupLayout.DEFAULT_SIZE, DEF_WIDTH)
                      .addComponent(searchButton, GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                      .addGroup(layout.createParallelGroup(
                              GroupLayout.Alignment.LEADING)
                                      .addGroup(layout.createSequentialGroup()
                                                      .addComponent(textLabel)
                                                      .addComponent(checkbox))
                                      .addComponent(ap)
                                      .addComponent(searchButton))
        );
    }


    /**
     * The panel that interfaces with the Autocomplete object.  It consists
     * of a search bar that text can be entered into, and a drop-down list
     * of suggestions auto-completing the user's query.
     */
    private class AutocompletePanel extends JPanel {
        // for serializable classes
        private static final long serialVersionUID = 1L;
        private static final int BOTTOM_MARGIN = 5;   // extra room to leave at the bottom

        private final JTextField searchText;      // the search bar
        private Autocomplete auto;                // the Autocomplete object
        private String[] results = new String[k]; // an array of matches
        private JList<String> suggestions;        // a list of autocomplete matches
        private JScrollPane scrollPane;           // the scroll bar on the side of the
        private JPanel suggestionsPanel;          // the dropdown menu of suggestions
        // the suggestion drop-down below the
        // last suggestion

        // change how this is implemented so it is dynamic;
        // shouldn't have to define a column number.

        // Keep these next two values in sync! - used to keep the search box
        // the same width as the drop-down
        // DEF_COLUMNS should be the number of characters in suggListLen

        // number of columns in the search text that is kept
        private static final int DEF_COLUMNS = 45;

        // an example of one of the longest strings in the database
        private static final String suggListLen =
                "<b>Harry Potter and the Deathly Hallows: Part 1 (2010)</b>";

        /**
         * Creates the Autocomplete object and the search bar and suggestion
         * drop-down portions of the GUI
         *
         * @param filename the file the Autocomplete object is constructed from
         */
        public AutocompletePanel(String filename) {
            super();

            // Read in the data
            Term[] terms;
            try {
                In in = new In(filename);
                String line0 = in.readLine();
                if (line0 == null) {
                    throw new IllegalArgumentException("Could not read line 0 of " + filename);
                }
                int n = Integer.parseInt(line0);
                terms = new Term[n];
                for (int i = 0; i < n; i++) {
                    String line = in.readLine();
                    if (line == null) {
                        throw new IllegalArgumentException(
                                "Could not read line " + (i + 1) + " of " + filename);
                    }
                    int tab = line.indexOf('\t');
                    if (tab == -1) {
                        throw new IllegalArgumentException(
                                "No tab character in line " + (i + 1) + " of " + filename);
                    }
                    long weight = Long.parseLong(line.substring(0, tab).trim());
                    String query = line.substring(tab + 1);
                    terms[i] = new Term(query, weight);
                }
            }
            catch (Exception e) {
                throw new IllegalArgumentException(
                        "Could not read or parse input file " + filename);
            }

            // Create the autocomplete object
            auto = new Autocomplete(terms);

            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);

            // create the search text, and allow the user to interact with it
            searchText = new JTextField(DEF_COLUMNS);
            searchText.setMaximumSize(new Dimension(
                    searchText.getMaximumSize().width,
                    searchText.getPreferredSize().height));
            searchText.getInputMap().put(KeyStroke.getKeyStroke("UP"), "none");
            searchText.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "none");
            searchText.addFocusListener(
                    new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            int pos = searchText.getText().length();
                            searchText.setCaretPosition(pos);
                        }

                        public void focusLost(FocusEvent e) {
                        }
                    });

            // create the search text box
            JPanel searchTextPanel = new JPanel();
            searchTextPanel.add(searchText);
            searchTextPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            searchTextPanel.setLayout(new GridLayout(1, 1));

            // create the drop-down menu items
            int fontsize = 13;
            int cellHeight = 20;

            suggestions = new JList<String>(results);
            suggestions.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            suggestions.setVisible(false);
            suggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            suggestions.setMaximumSize(new Dimension(
                    searchText.getMaximumSize().width,
                    suggestions.getPreferredSize().height));

            // Set to make equal to the width of the textfield
            suggestions.setPrototypeCellValue(suggListLen);
            suggestions.setFont(
                    suggestions.getFont().deriveFont(Font.PLAIN, fontsize));
            suggestions.setFixedCellHeight(cellHeight);

            // add arrow-key interactivity to the drop-down menu items
            Action makeSelection = new AbstractAction() {
                // for serializable classes
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e) {
                    if (!suggestions.isSelectionEmpty()) {
                        String selection = suggestions.getSelectedValue();
                        if (displayWeights)
                            selection = selection.substring(
                                    0, selection.indexOf("<td width="));
                        selection = selection.replaceAll("\\<.*?>", "");
                        searchText.setText(selection);
                        getSuggestions(selection);
                    }
                    searchOnline(searchText.getText());
                }
            };
            Action moveSelectionUp = new AbstractAction() {
                // for serializable classes
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (suggestions.getSelectedIndex() >= 0) {
                        suggestions.requestFocusInWindow();
                        suggestions.setSelectedIndex(suggestions.getSelectedIndex() - 1);
                    }
                }
            };
            Action moveSelectionDown = new AbstractAction() {
                // for serializable classes
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e) {
                    if (suggestions.getSelectedIndex() != results.length) {
                        suggestions.requestFocusInWindow();
                        suggestions.setSelectedIndex(suggestions.getSelectedIndex() + 1);
                    }
                }
            };
            Action moveSelectionUpFocused = new AbstractAction() {
                // for serializable classes
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e) {
                    if (suggestions.getSelectedIndex() == 0) {
                        suggestions.clearSelection();
                        searchText.requestFocusInWindow();
                        searchText.setSelectionEnd(0);
                    }
                    else if (suggestions.getSelectedIndex() >= 0) {
                        suggestions.setSelectedIndex(suggestions.getSelectedIndex() - 1);
                    }
                }
            };
            suggestions.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke("UP"), "moveSelectionUp");
            suggestions.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke("DOWN"), "moveSelectionDown");
            suggestions.getInputMap(JComponent.WHEN_FOCUSED).put(
                    KeyStroke.getKeyStroke("ENTER"), "makeSelection");
            suggestions.getInputMap().put(
                    KeyStroke.getKeyStroke("UP"), "moveSelectionUpFocused");
            suggestions.getActionMap().put("moveSelectionUp", moveSelectionUp);
            suggestions.getActionMap().put("moveSelectionDown", moveSelectionDown);
            suggestions.getActionMap().put("moveSelectionUpFocused", moveSelectionUpFocused);
            suggestions.getActionMap().put("makeSelection", makeSelection);

            // Create the suggestion drop-down panel and scroll bar
            suggestionsPanel = new JPanel();

            scrollPane = new JScrollPane(suggestions);
            scrollPane.setVisible(false);
            int prefBarWidth = scrollPane.getVerticalScrollBar().getPreferredSize().width;
            suggestions.setPreferredSize(new Dimension(searchText.getPreferredSize().width, 0));
            scrollPane.setAutoscrolls(true);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

            // resize widths and heights of all components to fit nicely
            int preferredWidth = searchText.getPreferredSize().width + 2 * prefBarWidth;
            int maxWidth = searchText.getMaximumSize().width + 2 * prefBarWidth;
            int searchBarHeight = searchText.getPreferredSize().height;
            int suggestionHeight = suggestions.getFixedCellHeight();
            int maxSuggestionHeight = DEF_HEIGHT * 2;

            suggestionsPanel.setPreferredSize(new Dimension(preferredWidth, suggestionHeight));
            suggestionsPanel.setMaximumSize(new Dimension(maxWidth, maxSuggestionHeight));
            suggestionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            suggestionsPanel.add(scrollPane);
            suggestionsPanel.setLayout(new GridLayout(1, 1));

            this.setPreferredSize(new Dimension(preferredWidth, this.getPreferredSize().height));
            this.setMaximumSize(
                    new Dimension(preferredWidth, searchBarHeight + maxSuggestionHeight));

            searchTextPanel.setPreferredSize(new Dimension(preferredWidth, searchBarHeight));
            searchTextPanel.setMaximumSize(new Dimension(maxWidth, searchBarHeight));
            searchText.setMaximumSize(new Dimension(maxWidth, searchBarHeight));

            // add mouse interactivity with the drop-down menu
            suggestions.addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                            if (mouseEvent.getClickCount() >= 1) {
                                int index = suggestions.locationToIndex(mouseEvent.getPoint());
                                if (index >= 0) {
                                    String selection = getSelectedText();
                                    searchText.setText(selection);
                                    String text = searchText.getText();
                                    getSuggestions(text);
                                    searchOnline(searchText.getText());
                                }
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent mouseEvent) {
                            int index = suggestions.locationToIndex(mouseEvent.getPoint());
                            suggestions.requestFocusInWindow();
                            suggestions.setSelectedIndex(index);
                        }

                        @Override
                        public void mouseExited(MouseEvent mouseEvent) {
                            suggestions.clearSelection();
                            searchText.requestFocusInWindow();
                        }
                    });
            suggestions.addMouseMotionListener(
                    new MouseInputAdapter() {
                        @Override

                        // Google a term when a user clicks on the dropdown menu
                        public void mouseClicked(MouseEvent mouseEvent) {
                            if (mouseEvent.getClickCount() >= 1) {
                                int index = suggestions.locationToIndex(mouseEvent.getPoint());
                                if (index >= 0) {
                                    String selection = getSelectedText();
                                    searchText.setText(selection);
                                    String text = searchText.getText();
                                    getSuggestions(text);
                                    searchOnline(searchText.getText());
                                }
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent mouseEvent) {
                            int index = suggestions.locationToIndex(mouseEvent.getPoint());
                            suggestions.requestFocusInWindow();
                            suggestions.setSelectedIndex(index);
                        }

                        @Override
                        public void mouseMoved(MouseEvent mouseEvent) {
                            int index = suggestions.locationToIndex(mouseEvent.getPoint());
                            suggestions.requestFocusInWindow();
                            suggestions.setSelectedIndex(index);
                        }
                    });

            // add a listener that allows updates each time the user types
            searchText.getDocument().addDocumentListener(
                    new DocumentListener() {
                        public void insertUpdate(DocumentEvent e) {
                            changedUpdate(e);
                        }

                        public void removeUpdate(DocumentEvent e) {
                            changedUpdate(e);
                        }

                        public void changedUpdate(DocumentEvent e) {
                            String text = searchText.getText();

                            // updates the drop-down menu
                            getSuggestions(text);
                            updateListSize();
                        }
                    });

            // When a user clicks on a suggestion, Google it
            searchText.addActionListener(
                    e -> {
                        String selection = getSelectedText();
                        searchText.setText(selection);
                        getSuggestions(selection);
                        searchOnline(searchText.getText());
                    });

            // Define the layout of the text box and suggestion dropdown
            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                          .addGroup(layout.createParallelGroup(
                                  GroupLayout.Alignment.LEADING)
                                          .addComponent(searchTextPanel, 0,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE)
                                          .addComponent(suggestionsPanel,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE))

            );

            layout.setVerticalGroup(
                    layout.createSequentialGroup()
                          .addComponent(searchTextPanel)
                          .addComponent(suggestionsPanel)
            );
        }

        /**
         * Re-populates the drop-down menu with the new suggestions, and
         * resizes the containing panel vertically
         */
        private void updateListSize() {
            int rows = k;
            if (suggestions.getModel().getSize() < k) {
                rows = suggestions.getModel().getSize();
            }

            int suggWidth = searchText.getPreferredSize().width;
            int suggPanelWidth = suggestionsPanel.getPreferredSize().width;
            int suggHeight = rows * suggestions.getFixedCellHeight();

            suggestions.setPreferredSize(new Dimension(suggWidth, suggHeight));
            suggestionsPanel
                    .setPreferredSize(new Dimension(suggPanelWidth, suggHeight + BOTTOM_MARGIN));
            suggestionsPanel
                    .setMaximumSize(new Dimension(suggPanelWidth, suggHeight + BOTTOM_MARGIN));

            // redraw the suggestion panel
            suggestionsPanel.setVisible(false);
            suggestionsPanel.setVisible(true);
        }

        // see getSuggestions for documentation
        public void update() {
            getSuggestions(searchText.getText());
        }

        /**
         * Makes a call to the implementation of Autocomplete to get
         * suggestions for the currently entered text.
         *
         * @param text string to search for
         */
        public void getSuggestions(String text) {

            // don't search for suggestions if there is no input
            if (text.equals("")) {
                suggestions.setListData(new String[0]);
                suggestions.clearSelection();
                suggestions.setVisible(false);
                scrollPane.setVisible(false);
            }
            else {
                // get all matching terms
                Term[] allResults = auto.allMatches(text);
                if (allResults == null) {
                    throw new NullPointerException("allMatches() is null");
                }

                results = new String[Math.min(k, allResults.length)];
                if (Math.min(k, allResults.length) > 0) {
                    for (int i = 0; i < results.length; i++) {

                        // A bit of a hack to get the Term's query string
                        // and weight from toString()
                        String next = allResults[i].toString();
                        if (allResults[i] == null) {
                            throw new NullPointerException("allMatches() "
                                                                   + "returned an array with a null entry");
                        }
                        int tab = next.indexOf('\t');
                        if (tab < 0) {
                            throw new RuntimeException("allMatches() returned"
                                                               + " an array with an entry without a tab:"
                                                               + " '" + next + "'");
                        }

                        // truncate length if needed
                        String query = next.substring(tab);
                        if (query.length() > suggListLen.length())
                            query = query.substring(0, suggListLen.length());

                        // create the table HTML
                        int textLen = text.length();
                        results[i] = "<html><table width=\""
                                + searchText.getPreferredSize().width + "\">"
                                + "<tr><td align=left>"
                                + query.substring(0, textLen + 1)
                                + "<b>" + query.substring(textLen + 1) + "</b>";
                        if (displayWeights) {
                            String weight = next.substring(0, tab).trim();
                            results[i] += "<td width=\"10%\" align=right>"
                                    + "<font size=-1><span id=\"weight\" "
                                    + "style=\"float:right;color:gray\">"
                                    + weight + "</font>";
                        }
                        results[i] += "</table></html>";
                    }
                    suggestions.setListData(results);
                    suggestions.setVisible(true);
                    scrollPane.setVisible(true);
                }
                else {
                    // No suggestions
                    suggestions.setListData(new String[0]);
                    suggestions.clearSelection();
                    suggestions.setVisible(false);
                    scrollPane.setVisible(false);
                }
            }
        }

        // bring the clicked suggestion up to the Search bar and search it
        public String getSelectedText() {
            if (!suggestions.isSelectionEmpty()) {
                String selection = suggestions.getSelectedValue();
                if (displayWeights) {
                    selection = selection.substring(0, selection.indexOf("<td width="));
                }
                selection = selection.replaceAll("\\<.*?>", "");
                selection = selection.replaceAll("^[ \t]+|[ \t]+$", "");
                return selection;
            }
            else {
                return getSearchText();
            }
        }

        public String getSearchText() {
            return searchText.getText();
        }
    }

    /**
     * Creates a URI from the user-defined string and searches the web with the
     * selected search engine
     * Opens the default web browser (or a new tab if it is already open)
     *
     * @param s string to search online for
     */
    private void searchOnline(String s) {
        try {
            URI tempAddress = new URI(SEARCH_URL + URLEncoder.encode(s.trim(), "UTF-8"));
            URI searchAddress = new URI(tempAddress.toASCIIString()); // Hack to handle Unicode
            Desktop.getDesktop().browse(searchAddress);
        }
        catch (URISyntaxException | IOException e) {
            throw new RuntimeException("could not open " + s + " in browser");
        }
    }

    /**
     * Creates an AutocompleteGUI object and start it continuously running
     *
     * @param args the filename from which the Autocomplete object is populated
     *             and the integer k which defines the maximum number of objects in the
     *             dropdown menu
     */
    public static void main(String[] args) {
        final String filename = args[0];
        final int k = Integer.parseInt(args[1]);
        SwingUtilities.invokeLater(
                () -> new AutocompleteGUI(filename, k).setVisible(true));
    }
}
