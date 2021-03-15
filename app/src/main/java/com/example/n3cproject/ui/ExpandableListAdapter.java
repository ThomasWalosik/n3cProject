package com.example.n3cproject.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.core.content.ContextCompat;
import com.example.n3cproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.n3cproject.ui.MainActivity.*;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<String> listDataHeader;
    private final String listDataChild;
    private final MainActivity activity;
    private Spinner spinner_age, spinner_type_cancer, spinner_poids;
    private EditText ed_prenom;
    boolean prenomHasFocus=false; // focus des edit text
    private final ArrayList<String> listeAge = new ArrayList<>();
    private final ArrayList<String> listeType = new ArrayList<>();
    private final ArrayList<String> listePoids = new ArrayList<>();
    LayoutInflater infalInflater;
    //GetSpinnerContent spinner = new GetSpinnerContent(age);
    TextView button_valider, button_reinit;
    SharedPreferences prefs;
    static Boolean isClick_age = false;
    Boolean isClick_poids = false;
    Boolean isClick_type = false;
    static Integer position_age = 0;
    Integer position_poids = 0;
    Integer position_type = 0;
    String pref_prenom = "";


    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 String listChildData, MainActivity activity, SharedPreferences prefs) {
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.activity = activity;
        this.context = context;
        this.prefs = prefs;

        System.out.println("JE SUIS DANS LE EXPANDABLE_LIST_ADAPTER.java");

        /*if (isOp.txt == 0) { // item fermé
            System.out.println("JE SUIS DANS LE EXPANDABLE_LIST_ADAPTER.java, (if) retourne de la valeur de isOp.txt="+isOp.txt);
            isOp.txt = 1;
        System.out.println(" qui devient mientenant isOp.txt="+isOp.txt);

        } else { // item ouvert
            System.out.println("JE SUIS DANS LE EXPANDABLE_LIST_ADAPTER.java, (else) retourne de la valeur de isOp.txt="+isOp.txt);
            isOp.txt = 0;
            System.out.println(" qui devient mientenant isOp.txt="+isOp.txt);
        }*/

        // init de la liste des âges
        listeAge.add("Âge");
        for (int i = 15; i < 25; i++) {
            listeAge.add(i +" ans");
        }
        // init de la liste des poids
        listePoids.add("Poids");
        for (int i=35; i<99; i++) {
            listePoids.add(i+" kg");
        }

        //init de la liste des types de cancer
        listeType.add("Type de cancer");
        listeType.add("Cancer du sein"); //TODO enum avec la liste des types de cancer
        listeType.add("Cancer des ovaires");
        listeType.add("Cancer du poumon");
        listeType.add("Cancer de la prostate");
        listeType.add("Cancer pédiatrique");

    }

    @Override
    public void onGroupExpanded(int groupPosition){
        //collapse the old expanded group, if not the same
        //as new group to expand
        super.onGroupExpanded(groupPosition);
        //lastExpandedGroupPosition = groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Méthode qui est appelée à l'ouverture du premier item parent de l'ExpandableListView (Informations Personnelles) pour creer ou modifier les items fils (EditText Nom, Prenom - Spinner Semestre, Niveau, Cursus, Groupe).
     * @param groupPosition correspond à la position de l'item parent (sélectionné ou non) dans l'ExpandableListView dans le NavigationView (dans cette fonction, seul la valeur groupPosition=0 (position de l'item Informations Personnelles) sera traité).
     * @param childPosition correspond à la position de l'item fils (sélectionné ou non) de l'item parent de la position groupPosition.
     * @param isLastChild booléen indiquant s'il s'agit du dernier item fils dans la liste des items fils.
     * @param convertView l'ancienne vue à réutiliser, si possible.
     * @param parent le parent auquel cette vue sera éventuellement attachée.
     * @return la vue correspondant au groupe à la position spécifiée (ici 0).
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (groupPosition == 0) {
            switch (childPosition) {
                case 0: // case correspondant à l'item fils Prenom (EditText) de l'item parent Informations Personnelles

                    infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.childexplv_prenom, parent, false);
                    ed_prenom = convertView.findViewById(R.id.editText_prenom);

                    ed_prenom.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void afterTextChanged(Editable mEdit)
                        {
                            prenom = mEdit.toString();
                            Donnees.userName = mEdit.toString();
                            //prefs.edit().putString("prenom", prenom).commit();
                            System.out.println("prenom = "+prenom);
                            System.out.println("userName = "+Donnees.userName);
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after){}

                        public void onTextChanged(CharSequence s, int start, int before, int count){}
                    });
                    pref_prenom = prenom;
                    ed_prenom.setText(prenom);
                    //System.out.println("getChildView(0) prenom = "+prenom);
                    ed_prenom.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));

                    break;

                case 1: // case correspondant à l'item fils Age (Spinner) de l'item parent Informations Personnelles

                    infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.childexplv_age, parent, false);
                    spinner_age = convertView.findViewById(R.id.spinner_age);
                    position_age = prefs.getInt("ageID", 0);

                    final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, listeAge) {
                        // personnalisation de la couleur de l'item selectionné du spinner_age
                        public View getView(int position, View convertView, ViewGroup parent) {
                            Donnees.ageItemPosition = position;
                            //System.out.println("Position = = "+position);

                            if (position != 0 && !isClick_age) {
                                isClick_age = true;
                                position_age = position;
                            }
                            if (position == 0 && isClick_age) {
                                isClick_age = false;
                            }
                            TextView tv = (TextView) super.getView(position_age, convertView, parent);

                            if (position_age == 0) {
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey));
                            } else {
                                if (isClick_age) {
                                    spinner_age.setSelection(position);
                                    tv.setText(spinner_age.getItemAtPosition(position).toString());
                                } else {
                                    spinner_age.setSelection(position_age);
                                    tv.setText(spinner_age.getItemAtPosition(position_age).toString());
                                }
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));
                            }
                            return tv;
                        }

                        @Override //affiche les items du déroulant du spinner
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                            //Si == "Age"
                            if (position == 0) {
                                tv.setText(spinner_age.getItemAtPosition(position).toString());
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey));
                            }
                            return tv;
                        }
                    };

                    spinner_age.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            //pour masquer le clavier si ouvert avant
                            hideKeyboard(v);
                            return false;
                        }
                    });
                    arrayAdapter_age.notifyDataSetChanged();
                    spinner_age.setAdapter(arrayAdapter_age);
                    break;

                case 2: // case correspondant à l'item fils Poids (Spinner) de l'item parent Informations Personnelles

                    infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.childexplv_poids, parent, false);
                    spinner_poids = convertView.findViewById(R.id.spinner_poids);
                    position_poids = prefs.getInt("poidsID", 0);

                    final ArrayAdapter<String> arrayAdapter_poids = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, listePoids) {
                        // personnalisation de la couleur de l'item selectionné du spinner_age
                        public View getView(int position, View convertView, ViewGroup parent) {
                            Donnees.poidsItemPosition = position;
                            if (position != 0 && !isClick_poids) {
                                isClick_poids = true;
                                position_poids = position;
                            }
                            if (position == 0 && isClick_poids) {
                                isClick_poids = false;
                            }
                            TextView tv = (TextView) super.getView(position_poids, convertView, parent);

                            if (position_poids == 0) {
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey));
                            } else {
                                if (isClick_poids) {
                                    spinner_poids.setSelection(position);
                                    tv.setText(spinner_poids.getItemAtPosition(position).toString());
                                } else {
                                    spinner_poids.setSelection(position_poids);
                                    tv.setText(spinner_poids.getItemAtPosition(position_poids).toString());
                                }
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));
                            }
                            System.out.println("ertyuiopqsdfghjklmwxcvbbn");
                            return tv;
                        }

                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                            //Si == "Poids"
                            if (position == 0) {
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey));
                            }
                            return tv;
                        }
                    };

                    spinner_poids.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            hideKeyboard(v);
                            return false;
                        }
                    });

                    arrayAdapter_poids.notifyDataSetChanged();
                    spinner_poids.setAdapter(arrayAdapter_poids);
                    break;

                case 3: // case correspondant à l'item fils TypeCancer (Spinner) de l'item parent Informations Personnelles

                    infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.childexplv_type_cancer, parent, false);
                    spinner_type_cancer = convertView.findViewById(R.id.spinner_type_cancer);
                    position_type = prefs.getInt("typeCancerID", 0);

                    final ArrayAdapter<String> arrayAdapter_type = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, listeType) {
                        // personnalisation de la couleur de l'item selectionné du spinner_type
                        public View getView(int position, View convertView, ViewGroup parent) {
                            Donnees.typeItemPosition = position;

                            if (position != 0 && !isClick_type) {
                                isClick_type = true;
                                position_type = position;
                            }
                            if (position == 0 && isClick_type) {
                                isClick_type = false;
                            }
                            TextView tv = (TextView) super.getView(position_type, convertView, parent);

                            if (position_type == 0) {
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey));
                            } else {
                                if (isClick_type) {
                                    spinner_type_cancer.setSelection(position);
                                    tv.setText(spinner_type_cancer.getItemAtPosition(position).toString());
                                } else {
                                    spinner_type_cancer.setSelection(position_type);
                                    tv.setText(spinner_type_cancer.getItemAtPosition(position_type).toString());
                                }
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));
                            }
                            return tv;
                        }

                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                            //Si == "Type de cancer"
                            if (position == 0) {
                                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey));
                            }
                            return tv;
                        }
                    };

                    spinner_type_cancer.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            hideKeyboard(v);
                            return false;
                        }
                    });

                    arrayAdapter_type.notifyDataSetChanged();
                    spinner_type_cancer.setAdapter(arrayAdapter_type);
                    break;

                default:
                    break;
            }
        }
        return convertView;
    }

    /**
     * Méthode qui renvoie le nombre d'enfants de l'item à la position groupPosition.
     * @param groupPosition correspond à la position du l'item.
     * @return nombre d'enfants de l'item à la position groupPosition.
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition==0) {
            return 4;
        }
        return 0;
    }

    public String getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    /**
     * Méthode qui obtient une vue qui affiche le groupe donné. Cette vue est uniquement pour le groupe (les vues des enfants du groupe seront récupérées en utilisant getChildView()).
     * @param groupPosition correspond à la position du groupe pour lequel la vue est retournée.
     * @param isExpanded booléen indiquant si le groupe est développé ou comprimé.
     * @param convertView l'ancienne vue à réutiliser, si possible.
     * @param parent le parent auquel cette vue sera éventuellement attachée.
     * @return la vue correspondant au groupe à la position spécifiée.
     */
    @SuppressLint("CommitPrefEdits")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        switch (groupPosition) {
            case 0: // case correspondant à l'item père Informations Personnelles
                if (getIsOpened() == 0) {
                    // si le groupe Information Personnelles est fermé, on l'initialise avec le bon layout (et la fleche vers le bas)
                    infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.line_infoperso_closed, parent, false);
                    //Donnees.ageItemPosition = position_age;
                    //Donnees.poidsItemPosition = position_poids;
                    //Donnees.typeItemPosition = position_type;
                    position_age2 = Donnees.ageItemPosition;
                    position_poids2 = Donnees.poidsItemPosition; //position_poids
                    position_type2 = Donnees.typeItemPosition; //position_type
                    prenom = Donnees.userName;
                    System.out.println("LLLAAAAAAA quand on ouvre ??? = "+prenom);
                    // enregistrement de l'age, du poids et du typeCancer a la fermeture de l'item Information Personnelles
                    MainActivity.setPreferencesInfoPerso();

                } else {
                    // si le groupe Information Personnelles est ouvert, on l'initialise avec le bon layout (et la fleche vers le haut)
                    infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.line_infoperso_ok_opened, parent, false);
                    // remplissage des spinners de l'âge, poids, et typeCancer a l'ouverture de l'item Information Personelles
                    MainActivity.getPreferencesInfoPerso();
                    System.out.println("dans le elseeeeeeeeeeeeeeeee");
                }
                break;

            case 1: // case correspondant à l'item père Mon ordonnance
                infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.line_ordonnance, parent, false);
                break;

            case 2: // case correspondant à l'item père Espace Meditation
                infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.line_meditation, parent, false);

                break;

            case 3: // case correspondant à l'item père Conditions d'Utilisations
                infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.line_condition, parent, false);
                break;

            case 4: // case correspondant à l'item père Aide
                infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.line_aide, parent, false);
                break;

            case 5: // case correspondant à l'item père A Propos
                infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.line_propos, parent, false);
                break;

            case 6: // case correspondant à l'item père Version
                infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.line_version, parent, false);
                break;

            default:
                break;
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * methode qui cache le clavier en fonction de la vue passée en parametre.
     * @param view correspond à la vue actuelle.
     * @return rien.
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * methode qui affiche le clavier en fonction de la vue passée en parametre.
     * @param view correspond à la vue actuelle.
     * @return rien.
     */
    public void showKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }
}